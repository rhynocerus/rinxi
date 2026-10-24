package com.rhynus.rinxi.probe;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Process;
import android.os.StatFs;
import android.system.Os;
import android.system.StructUtsname;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class SystemProbe {

    private final Context context;

    public SystemProbe(Context context) {
        this.context = context;
    }

    private String line(String key, String value) {
        return String.format(
                Locale.US,
                "%-18s : %s\n",
                key,
                value == null || value.isEmpty()
                        ? "[UNKNOWN]"
                        : value
        );
    }

    private String section(String name) {
        return "\n[ " + name + " ]\n";
    }

    public String system() {

        StringBuilder r = new StringBuilder();

        r.append(section("SYSTEM"));

        r.append(line("Manufacturer", Build.MANUFACTURER));
        r.append(line("Brand", Build.BRAND));
        r.append(line("Model", Build.MODEL));
        r.append(line("Device", Build.DEVICE));
        r.append(line("Product", Build.PRODUCT));
        r.append(line("Hardware", Build.HARDWARE));

        r.append(section("ANDROID"));

        r.append(line("Android", Build.VERSION.RELEASE));
        r.append(line("API", String.valueOf(Build.VERSION.SDK_INT)));
        r.append(line("Security patch", Build.VERSION.SECURITY_PATCH));
        r.append(line("Build ID", Build.ID));

        return r.toString();
    }

    public String uname(boolean full) {

        try {

            StructUtsname u = Os.uname();

            if (!full) {
                return u.sysname + " " + u.release + "\n";
            }

            return u.sysname + " "
                    + u.nodename + " "
                    + u.release + " "
                    + u.version + " "
                    + u.machine + "\n";

        } catch (Exception e) {

            return "[RESTRICTED] uname unavailable\n";
        }
    }

    public String whoami() {

        StringBuilder r = new StringBuilder();

        r.append(section("APP SANDBOX"));

        r.append(line(
                "Package",
                context.getPackageName()
        ));

        r.append(line(
                "UID",
                String.valueOf(Process.myUid())
        ));

        r.append(line(
                "Root access",
                "NOT REQUESTED"
        ));

        r.append(line(
                "Internet",
                "NOT DECLARED"
        ));

        return r.toString();
    }

    public String cpu() {

        StringBuilder r = new StringBuilder();

        r.append(section("CPU"));

        if (Build.VERSION.SDK_INT >= 31) {

            r.append(line(
                    "SoC model",
                    Build.SOC_MODEL
            ));

            r.append(line(
                    "SoC maker",
                    Build.SOC_MANUFACTURER
            ));
        }

        r.append(line(
                "Cores",
                String.valueOf(
                        Runtime.getRuntime()
                                .availableProcessors()
                )
        ));

        r.append(line(
                "ABI",
                String.join(
                        ", ",
                        Build.SUPPORTED_ABIS
                )
        ));

        r.append(line(
                "Machine",
                machine()
        ));

        return r.toString();
    }

    private String machine() {

        try {
            return Os.uname().machine;
        } catch (Exception e) {
            return "[UNKNOWN]";
        }
    }

    public String memory() {

        ActivityManager am =
                (ActivityManager)
                        context.getSystemService(
                                Context.ACTIVITY_SERVICE
                        );

        ActivityManager.MemoryInfo mi =
                new ActivityManager.MemoryInfo();

        am.getMemoryInfo(mi);

        StringBuilder r = new StringBuilder();

        r.append(section("MEMORY"));

        r.append(line(
                "Total",
                humanBytes(mi.totalMem)
        ));

        r.append(line(
                "Available",
                humanBytes(mi.availMem)
        ));

        r.append(line(
                "Threshold",
                humanBytes(mi.threshold)
        ));

        r.append(line(
                "Low memory",
                String.valueOf(mi.lowMemory)
        ));

        return r.toString();
    }

    public String storage() {

        File data =
                context.getFilesDir();

        StatFs fs =
                new StatFs(
                        data.getAbsolutePath()
                );

        StringBuilder r = new StringBuilder();

        r.append(section("STORAGE"));

        r.append(line(
                "Total",
                humanBytes(fs.getTotalBytes())
        ));

        r.append(line(
                "Available",
                humanBytes(fs.getAvailableBytes())
        ));

        r.append(line(
                "App path",
                data.getAbsolutePath()
        ));

        return r.toString();
    }

    @SuppressWarnings("deprecation")
    public String display() {

        StringBuilder r = new StringBuilder();

        r.append(section("DISPLAY"));

        WindowManager wm =
                (WindowManager)
                        context.getSystemService(
                                Context.WINDOW_SERVICE
                        );

        Display display =
                wm.getDefaultDisplay();

        Display.Mode mode =
                display.getMode();

        DisplayMetrics dm =
                context.getResources()
                        .getDisplayMetrics();

        r.append(line(
                "Physical",
                mode.getPhysicalWidth()
                        + "x"
                        + mode.getPhysicalHeight()
        ));

        r.append(line(
                "Refresh",
                String.format(
                        Locale.US,
                        "%.2f Hz",
                        mode.getRefreshRate()
                )
        ));

        r.append(line(
                "Density DPI",
                String.valueOf(dm.densityDpi)
        ));

        r.append(line(
                "Density scale",
                String.format(
                        Locale.US,
                        "%.2f",
                        dm.density
                )
        ));

        return r.toString();
    }

    public String battery() {

        StringBuilder r = new StringBuilder();

        r.append(section("BATTERY"));

        Intent battery =
                context.registerReceiver(
                        null,
                        new IntentFilter(
                                Intent.ACTION_BATTERY_CHANGED
                        )
                );

        if (battery == null) {

            r.append(
                    line(
                            "Battery",
                            "[UNAVAILABLE]"
                    )
            );

            return r.toString();
        }

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

        r.append(line(
                "Level",
                String.format(
                        Locale.US,
                        "%.0f%%",
                        percent
                )
        ));

        int temp =
                battery.getIntExtra(
                        BatteryManager.EXTRA_TEMPERATURE,
                        -1
                );

        if (temp >= 0) {

            r.append(line(
                    "Temperature",
                    String.format(
                            Locale.US,
                            "%.1f C",
                            temp / 10f
                    )
            ));
        }

        int voltage =
                battery.getIntExtra(
                        BatteryManager.EXTRA_VOLTAGE,
                        -1
                );

        if (voltage >= 1000) {

            r.append(line(
                    "Voltage",
                    voltage + " mV"
            ));

        } else if (voltage >= 0) {

            r.append(line(
                    "Voltage raw",
                    String.valueOf(voltage)
            ));

            r.append(line(
                    "Voltage scale",
                    "[VENDOR / UNKNOWN]"
            ));
        }

        int status =
                battery.getIntExtra(
                        BatteryManager.EXTRA_STATUS,
                        -1
                );

        r.append(line(
                "Status",
                batteryStatus(status)
        ));

        return r.toString();
    }

    private String batteryStatus(int status) {

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

    public String network() {

        StringBuilder r = new StringBuilder();

        r.append(section("NETWORK"));

        ConnectivityManager cm =
                (ConnectivityManager)
                        context.getSystemService(
                                Context.CONNECTIVITY_SERVICE
                        );

        try {

            Network network =
                    cm.getActiveNetwork();

            NetworkCapabilities caps =
                    cm.getNetworkCapabilities(network);

            if (caps == null) {

                r.append(line(
                        "Transport",
                        "OFFLINE / UNKNOWN"
                ));

            } else if (
                    caps.hasTransport(
                            NetworkCapabilities.TRANSPORT_WIFI
                    )
            ) {

                r.append(line(
                        "Transport",
                        "Wi-Fi"
                ));

            } else if (
                    caps.hasTransport(
                            NetworkCapabilities.TRANSPORT_CELLULAR
                    )
            ) {

                r.append(line(
                        "Transport",
                        "Cellular"
                ));

            } else if (
                    caps.hasTransport(
                            NetworkCapabilities.TRANSPORT_VPN
                    )
            ) {

                r.append(line(
                        "Transport",
                        "VPN"
                ));

            } else {

                r.append(line(
                        "Transport",
                        "Other"
                ));
            }

        } catch (Exception e) {

            r.append(line(
                    "Transport",
                    "[RESTRICTED]"
            ));
        }

        r.append(line(
                "Interfaces",
                "[ANDROID LIMITED]"
        ));

        return r.toString();
    }

    public String sensors() {

        SensorManager sm =
                (SensorManager)
                        context.getSystemService(
                                Context.SENSOR_SERVICE
                        );

        List<Sensor> sensors =
                sm.getSensorList(
                        Sensor.TYPE_ALL
                );

        StringBuilder r = new StringBuilder();

        r.append(section("SENSORS"));

        r.append(line(
                "Detected",
                String.valueOf(
                        sensors.size()
                )
        ));

        sensorLine(
                r,
                sm,
                Sensor.TYPE_ACCELEROMETER,
                "Accelerometer"
        );

        sensorLine(
                r,
                sm,
                Sensor.TYPE_GYROSCOPE,
                "Gyroscope"
        );

        sensorLine(
                r,
                sm,
                Sensor.TYPE_MAGNETIC_FIELD,
                "Magnetometer"
        );

        sensorLine(
                r,
                sm,
                Sensor.TYPE_PROXIMITY,
                "Proximity"
        );

        sensorLine(
                r,
                sm,
                Sensor.TYPE_LIGHT,
                "Light"
        );

        return r.toString();
    }

    private void sensorLine(
            StringBuilder r,
            SensorManager sm,
            int type,
            String label
    ) {

        Sensor s =
                sm.getDefaultSensor(type);

        r.append(line(
                label,
                s == null
                        ? "[NOT FOUND]"
                        : "[OK] " + s.getName()
        ));
    }

    public String security() {

        StringBuilder r = new StringBuilder();

        r.append(section("SECURITY"));

        r.append(line(
                "App sandbox",
                "[OK] ACTIVE"
        ));

        r.append(line(
                "App UID",
                String.valueOf(
                        Process.myUid()
                )
        ));

        r.append(line(
                "Root access",
                "NOT REQUESTED"
        ));

        try {

            r.append(line(
                    "Hardware serial",
                    Build.getSerial()
            ));

        } catch (SecurityException e) {

            r.append(line(
                    "Hardware serial",
                    "[RESTRICTED]"
            ));
        }

        r.append(line(
                "IMEI",
                "[NOT REQUESTED]"
        ));

        r.append(line(
                "Verified Boot",
                "[PRIVILEGED PROPERTY]"
        ));

        r.append(line(
                "Bootloader",
                "[PRIVILEGED PROPERTY]"
        ));

        r.append(line(
                "App inventory",
                "[ANDROID LIMITED]"
        ));

        return r.toString();
    }

    public String full() {

        StringBuilder r = new StringBuilder();

        r.append(
                "========================================\n"
        );

        r.append(
                "       RINXI :: SYSTEM PROBE v0.2\n"
        );

        r.append(
                "========================================\n"
        );

        r.append(system());
        r.append(cpu());

        r.append(section("KERNEL"));
        r.append(uname(true));

        r.append(memory());
        r.append(storage());
        r.append(display());
        r.append(battery());
        r.append(network());
        r.append(sensors());
        r.append(whoami());
        r.append(security());

        r.append(
                "\n========================================\n"
        );

        r.append(
                "PROBE COMPLETE\n"
        );

        r.append(
                "========================================\n"
        );

        return r.toString();
    }

    public String json() {

        try {

            JSONObject root =
                    new JSONObject();

            root.put(
                    "schema",
                    "rinxi.system-report"
            );

            root.put(
                    "version",
                    1
            );

            JSONObject device =
                    new JSONObject();

            device.put(
                    "manufacturer",
                    Build.MANUFACTURER
            );

            device.put(
                    "brand",
                    Build.BRAND
            );

            device.put(
                    "model",
                    Build.MODEL
            );

            device.put(
                    "device",
                    Build.DEVICE
            );

            device.put(
                    "product",
                    Build.PRODUCT
            );

            root.put(
                    "device",
                    device
            );

            JSONObject android =
                    new JSONObject();

            android.put(
                    "version",
                    Build.VERSION.RELEASE
            );

            android.put(
                    "api",
                    Build.VERSION.SDK_INT
            );

            android.put(
                    "securityPatch",
                    Build.VERSION.SECURITY_PATCH
            );

            root.put(
                    "android",
                    android
            );

            JSONObject cpu =
                    new JSONObject();

            if (Build.VERSION.SDK_INT >= 31) {

                cpu.put(
                        "soc",
                        Build.SOC_MODEL
                );

                cpu.put(
                        "manufacturer",
                        Build.SOC_MANUFACTURER
                );
            }

            cpu.put(
                    "cores",
                    Runtime.getRuntime()
                            .availableProcessors()
            );

            JSONArray abi =
                    new JSONArray();

            for (
                    String item :
                    Build.SUPPORTED_ABIS
            ) {

                abi.put(item);
            }

            cpu.put(
                    "abi",
                    abi
            );

            root.put(
                    "cpu",
                    cpu
            );

            root.put(
                    "sandboxUid",
                    Process.myUid()
            );

            root.put(
                    "internetPermission",
                    false
            );

            return root.toString(2) + "\n";

        } catch (Exception e) {

            return "{\"error\":\"JSON generation failed\"}\n";
        }
    }

    private String humanBytes(long bytes) {

        double gib =
                bytes
                        / 1024.0
                        / 1024.0
                        / 1024.0;

        return String.format(
                Locale.US,
                "%.2f GiB",
                gib
        );
    }
}
