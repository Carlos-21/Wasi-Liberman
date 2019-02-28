package aplicacion.liberman.com.wasiL2.servicio;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.contenedor.Hijo;
import aplicacion.liberman.com.wasiL2.controlador.Apoderado;

public class ServicioFirebase extends Service {
    public static String sIdentificadorApoderado;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String manufacturer = "xiaomi";
        if (manufacturer.equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
            //this will open auto start screen where user can enable permission for your app
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);
        }

        System.out.println("Identificacion 12138 : " + sIdentificadorApoderado);
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();
        if (oFirestore != null) {
            oFirestore.collection("Niño")
                    .whereEqualTo("apoderado", sIdentificadorApoderado)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                            if (queryDocumentSnapshots != null) {
                                List<Hijo> listaHijos = queryDocumentSnapshots.toObjects(Hijo.class);

                                for (Hijo auxiliar : listaHijos) {
                                    if (auxiliar.isCasa()) {
                                        String mensaje = auxiliar.getApellidos() + " " + auxiliar.getNombres();
                                        if (auxiliar.getGenero().equals("masculino")) {
                                            notificacionEntregaNiño(mensaje, "niño");
                                        } else {
                                            notificacionEntregaNiño(mensaje, "niña");
                                        }
                                    }
                                }
                            }
                        }
                    });
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(),
                this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(
                getApplicationContext(), 1, restartServiceIntent,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext()
                .getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);
        super.onTaskRemoved(rootIntent);
    }

    /**
     * Método encargado de enviar la notificación al usuario mostrando un mensaje dependiendo
     * de los parámetros
     *
     * @param sMensaje
     * @param sGenero
     */
    private void notificacionEntregaNiño(String sMensaje, String sGenero) {
        NotificationCompat.Builder mBuilder;
        NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        int icono = R.mipmap.ic_launcher;
        Intent intent = new Intent(getBaseContext(), Apoderado.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);

        mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext(), "afs")
                .setContentIntent(pendingIntent)
                .setSmallIcon(icono)
                .setContentTitle("Niño entregado")
                .setContentText("Su " + sGenero + ": " + sMensaje + " esta en su casa.")
                .setVibrate(new long[]{100, 250, 100, 500})
                .setAutoCancel(true);

        mNotifyMgr.notify(1, mBuilder.build());
    }

}
