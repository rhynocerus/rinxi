package com.rhynus.rinxi;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;

import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.graphics.Color;
import android.graphics.Typeface;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.os.StatFs;

import android.system.Os;
import android.system.StructUtsname;

import android.util.DisplayMetrics;

import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import java.net.InetAddress;
import java.net.NetworkInterface;

import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {

    private TextView terminal;

    private static final int BG =
            Color.rgb(5, 10, 8);

    private static final int PANEL =
            Color.rgb(10, 20, 15);

    private static final int GREEN =
            Color.rgb(100, 255, 150);

    private static final int TEXT =
            Color.rgb(210, 255, 220);

    private static final int DIM =
            Color.rgb(130, 170, 140);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);

        createInterface();

        terminal.setText(
            "\nRINXI ready.\n\n" +
            "rhynus@android:~$ waiting_for_probe\n"
        );

        showIntro();
    }


    private void createInterface() {

        int pad = dp(16);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(pad, pad, pad, pad);
        root.setBackgroundColor(BG);
        root.setFitsSystemWindows(true);


        TextView title = new TextView(this);
        title.setText("RINXI");
        title.setTextSize(30);
        title.setTextColor(GREEN);
        title.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);

        root.addView(title);


        TextView subtitle = new TextView(this);
        subtitle.setText("RHYNUS SYSTEM PROBE // v0.1");
        subtitle.setTextSize(12);
        subtitle.setTextColor(DIM);
        subtitle.setTypeface(Typeface.MONOSPACE);

        root.addView(subtitle);


        TextView privacy = new TextView(this);
        privacy.setText(
            "\nLOCAL DIAGNOSTICS\n" +
            "NO ROOT  •  NO INTERNET PERMISSION\n"
        );
        privacy.setTextColor(TEXT);
        privacy.setTextSize(12);
        privacy.setTypeface(Typeface.MONOSPACE);

        root.addView(privacy);


        LinearLayout buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.HORIZONTAL);
        buttons.setGravity(Gravity.CENTER_VERTICAL);


        Button scan = new Button(this);
        scan.setText("SCAN");

        scan.setOnClickListener(v -> runProbe());


        Button copy = new Button(this);
        copy.setText("COPY");

        copy.setOnClickListener(v -> copyReport());


        buttons.addView(
            scan,
            new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1
            )
        );

        buttons.addView(
            copy,
            new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1
            )
        );

        root.addView(buttons);


        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(PANEL);


        terminal = new TextView(this);

        terminal.setPadding(
            dp(12),
            dp(12),
            dp(12),
            dp(24)
        );

        terminal.setTypeface(Typeface.MONOSPACE);
        terminal.setTextSize(12);
        terminal.setTextColor(TEXT);
        terminal.setTextIsSelectable(true);

        scroll.addView(terminal);


        root.addView(
            scroll,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1
            )
        );


        setContentView(root);
    }


    private void showIntro() {

        new AlertDialog.Builder(this)
            .setTitle("RINXI :: LOCAL SYSTEM PROBE")
            .setMessage(
                "RINXI inspects information Android makes available " +
                "to a normal application.\n\n" +

                "No root access.\n" +
                "No data is transmitted.\n" +
                "No INTERNET permission.\n\n" +

                "Unavailable information will be marked as " +
                "RESTRICTED rather than guessed."
            )
            .setNegativeButton(
                "CANCEL",
                null
            )
            .setPositiveButton(
                "RUN BASIC SCAN",
                (dialog, which) -> runProbe()
            )
            .show();
    }


    private void runProbe() {

        StringBuilder r = new StringBuilder();

        r.append(
            "========================================\n"
        );
        r.append(
            "       RINXI :: SYSTEM PROBE v0.1\n"
        );
        r.append(
            "========================================\n\n"
        );


        section(r, "IDENTITY");

        line(r, "Manufacturer", Build.MANUFACTURER);
        line(r, "Brand", Build.BRAND);
        line(r, "Model", Build.MODEL);
        line(r, "Device", Build.DEVICE);
        line(r, "Product", Build.PRODUCT);
        line(r, "Hardware", Build.HARDWARE);


        section(r, "ANDROID");

        line(
            r,
            "Android",
            Build.VERSION.RELEASE
        );

        line(
            r,
            "API",
            String.valueOf(Build.VERSION.SDK_INT)
        );

        line(
            r,
            "Security patch",
            Build.VERSION.SECURITY_PATCH
        );

        line(
            r,
            "Build ID",
            Build.ID
        );

        line(
            r,
            "Fingerprint",
            Build.FINGERPRINT
        );


        section(r, "SOC / CPU");

        if (Build.VERSION.SDK_INT >= 31) {

            line(
                r,
                "SoC model",
                Build.SOC_MODEL
            );

            line(
                r,
                "SoC maker",
                Build.SOC_MANUFACTURER
            );

        } else {

            line(
                r,
                "SoC",
                "[UNAVAILABLE API]"
            );
        }

        line(
            r,
            "CPU cores",
            String.valueOf(
                Runtime.getRuntime().availableProcessors()
            )
        );

        line(
            r,
            "ABI",
            String.join(", ", Build.SUPPORTED_ABIS)
        );


        section(r, "KERNEL");

        try {

            StructUtsname u = Os.uname();

            line(
                r,
                "Kernel",
                u.sysname + " " + u.release
            );

            line(
                r,
                "Machine",
                u.machine
            );

            line(
                r,
                "Node",
                u.nodename
            );

        } catch (Exception e) {

            line(
                r,
                "Kernel",
                "[RESTRICTED]"
            );
        }


        section(r, "MEMORY");

        ActivityManager am =
            (ActivityManager)
                getSystemService(ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo mi =
            new ActivityManager.MemoryInfo();

        am.getMemoryInfo(mi);

        line(
            r,
            "Total",
            humanBytes(mi.totalMem)
        );

        line(
            r,
            "Available",
            humanBytes(mi.availMem)
        );

        line(
            r,
            "Threshold",
            humanBytes(mi.threshold)
        );

        line(
            r,
            "Low memory",
            String.valueOf(mi.lowMemory)
        );


        section(r, "STORAGE");

        File data =
            getFilesDir();

        StatFs fs =
            new StatFs(data.getAbsolutePath());

        line(
            r,
            "Total",
            humanBytes(fs.getTotalBytes())
        );

        line(
            r,
            "Available",
            humanBytes(fs.getAvailableBytes())
        );

        line(
            r,
            "App path",
            data.getAbsolutePath()
        );


        section(r, "DISPLAY");

        Display display =
            getWindowManager().getDefaultDisplay();

        Display.Mode mode =
            display.getMode();

        line(
            r,
            "Physical",
            mode.getPhysicalWidth()
                + "x"
                + mode.getPhysicalHeight()
        );

        line(
            r,
            "Refresh",
            String.format(
                "%.2f Hz",
                mode.getRefreshRate()
            )
        );

        DisplayMetrics dm =
            getResources().getDisplayMetrics();

        line(
            r,
            "Density DPI",
            String.valueOf(dm.densityDpi)
        );

        line(
            r,
            "Density scale",
            String.valueOf(dm.density)
        );


        section(r, "BATTERY");

        Intent battery =
            registerReceiver(
                null,
                new IntentFilter(
                    Intent.ACTION_BATTERY_CHANGED
                )
            );

        if (battery != null) {

            int level =
                battery.getIntExtra(
                    BatteryManager.EXTRA_LEVEL,
                    -1
                );

            int scale =
                battery.getIntExtra(
                    BatteryManager.EXTRA_SCALE,
                    100
                );

            float percent =
                scale > 0
                    ? level * 100f / scale
                    : -1;

            line(
                r,
                "Level",
                String.format(
                    "%.0f%%",
                    percent
                )
            );

            int temp =
                battery.getIntExtra(
                    BatteryManager.EXTRA_TEMPERATURE,
                    -1
                );

            if (temp >= 0) {
                line(
                    r,
                    "Temperature",
                    String.format(
                        "%.1f °C",
                        temp / 10f
                    )
                );
            }

            int voltage =
                battery.getIntExtra(
                    BatteryManager.EXTRA_VOLTAGE,
                    -1
                );

            if (voltage >= 0) {
                line(
                    r,
                    "Voltage",
                    voltage + " mV"
                );
            }

            int status =
                battery.getIntExtra(
                    BatteryManager.EXTRA_STATUS,
                    -1
                );

            line(
                r,
                "Status",
                batteryStatus(status)
            );
        }


        section(r, "NETWORK");

        ConnectivityManager cm =
            (ConnectivityManager)
                getSystemService(
                    Context.CONNECTIVITY_SERVICE
                );

        try {

            Network active =
                cm.getActiveNetwork();

            NetworkCapabilities caps =
                cm.getNetworkCapabilities(active);

            if (caps == null) {

                line(
                    r,
                    "Transport",
                    "OFFLINE / UNKNOWN"
                );

            } else if (
                caps.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            ) {

                line(
                    r,
                    "Transport",
                    "Wi-Fi"
                );

            } else if (
                caps.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR
                )
            ) {

                line(
                    r,
                    "Transport",
                    "Cellular"
                );

            } else if (
                caps.hasTransport(
                    NetworkCapabilities.TRANSPORT_VPN
                )
            ) {

                line(
                    r,
                    "Transport",
                    "VPN"
                );

            } else {

                line(
                    r,
                    "Transport",
                    "Other"
                );
            }

        } catch (Exception e) {

            line(
                r,
                "Transport",
                "[RESTRICTED]"
            );
        }


        try {

            List<NetworkInterface> interfaces =
                Collections.list(
                    NetworkInterface.getNetworkInterfaces()
                );

            for (
                NetworkInterface iface :
                interfaces
            ) {

                if (!iface.isUp()) {
                    continue;
                }

                List<InetAddress> addresses =
                    Collections.list(
                        iface.getInetAddresses()
                    );

                for (
                    InetAddress addr :
                    addresses
                ) {

                    if (
                        !addr.isLoopbackAddress()
                    ) {

                        line(
                            r,
                            iface.getName(),
                            addr.getHostAddress()
                        );
                    }
                }
            }

        } catch (Exception e) {

            line(
                r,
                "Interfaces",
                "[RESTRICTED]"
            );
        }


        section(r, "SENSORS");

        SensorManager sm =
            (SensorManager)
                getSystemService(
                    SENSOR_SERVICE
                );

        List<Sensor> sensors =
            sm.getSensorList(
                Sensor.TYPE_ALL
            );

        line(
            r,
            "Detected",
            String.valueOf(
                sensors.size()
            )
        );

        sensorStatus(
            r,
            sm,
            Sensor.TYPE_ACCELEROMETER,
            "Accelerometer"
        );

        sensorStatus(
            r,
            sm,
            Sensor.TYPE_GYROSCOPE,
            "Gyroscope"
        );

        sensorStatus(
            r,
            sm,
            Sensor.TYPE_MAGNETIC_FIELD,
            "Magnetometer"
        );

        sensorStatus(
            r,
            sm,
            Sensor.TYPE_PROXIMITY,
            "Proximity"
        );

        sensorStatus(
            r,
            sm,
            Sensor.TYPE_LIGHT,
            "Light"
        );


        section(r, "APP SANDBOX");

        line(
            r,
            "Package",
            getPackageName()
        );

        line(
            r,
            "UID",
            String.valueOf(
                Process.myUid()
            )
        );

        line(
            r,
            "Root",
            "NO"
        );

        line(
            r,
            "Internet permission",
            "NOT DECLARED"
        );


        section(r, "RESTRICTED / PROTECTED");

        try {

            line(
                r,
                "Hardware serial",
                Build.getSerial()
            );

        } catch (
            SecurityException e
        ) {

            line(
                r,
                "Hardware serial",
                "[RESTRICTED]"
            );
        }

        line(
            r,
            "IMEI",
            "[NOT REQUESTED]"
        );

        line(
            r,
            "Verified Boot",
            "[PRIVILEGED PROPERTY]"
        );

        line(
            r,
            "Bootloader state",
            "[PRIVILEGED PROPERTY]"
        );

        line(
            r,
            "Full app inventory",
            "[ANDROID LIMITED]"
        );

        line(
            r,
            "Host USBGuard",
            "[OUTSIDE DEVICE]"
        );


        r.append(
            "\n========================================\n"
        );

        r.append(
            "SCAN COMPLETE\n"
        );

        r.append(
            "========================================\n"
        );


        terminal.setText(r.toString());
    }


    private void section(
        StringBuilder r,
        String name
    ) {

        r.append("\n[ ")
         .append(name)
         .append(" ]\n");
    }


    private void line(
        StringBuilder r,
        String key,
        String value
    ) {

        r.append(
            String.format(
                "%-18s : %s\n",
                key,
                value == null
                    ? "[UNKNOWN]"
                    : value
            )
        );
    }


    private void sensorStatus(
        StringBuilder r,
        SensorManager sm,
        int type,
        String name
    ) {

        Sensor s =
            sm.getDefaultSensor(type);

        line(
            r,
            name,
            s == null
                ? "[NOT FOUND]"
                : "[OK] " + s.getName()
        );
    }


    private String humanBytes(
        long bytes
    ) {

        double gib =
            bytes /
            1024.0 /
            1024.0 /
            1024.0;

        return String.format(
            "%.2f GiB",
            gib
        );
    }


    private String batteryStatus(
        int status
    ) {

        switch (status) {

            case BatteryManager.BATTERY_STATUS_CHARGING:
                return "CHARGING";

            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                return "DISCHARGING";

            case BatteryManager.BATTERY_STATUS_FULL:
                return "FULL";

            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                return "NOT CHARGING";

            default:
                return "UNKNOWN";
        }
    }


    private void copyReport() {

        ClipboardManager clipboard =
            (ClipboardManager)
                getSystemService(
                    Context.CLIPBOARD_SERVICE
                );

        ClipData clip =
            ClipData.newPlainText(
                "RINXI report",
                terminal.getText()
            );

        clipboard.setPrimaryClip(clip);

        Toast.makeText(
            this,
            "RINXI report copied",
            Toast.LENGTH_SHORT
        ).show();
    }


    private int dp(
        int value
    ) {

        return (int) (
            value *
            getResources()
                .getDisplayMetrics()
                .density
        );
    }
}
