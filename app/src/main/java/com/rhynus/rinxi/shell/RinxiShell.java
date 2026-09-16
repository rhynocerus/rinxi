package com.rhynus.rinxi.shell;

import android.content.Context;

import com.rhynus.rinxi.probe.SystemProbe;

import java.util.Locale;

public class RinxiShell {

    private final Context context;
    private final SystemProbe probe;

    public RinxiShell(Context context) {

        this.context = context;

        this.probe =
                new SystemProbe(context);
    }

    public static class Result {

        public final String text;
        public final boolean clear;

        public Result(
                String text,
                boolean clear
        ) {

            this.text = text;
            this.clear = clear;
        }
    }

    public Result execute(String raw) {

        String command =
                raw == null
                        ? ""
                        : raw.trim()
                             .replaceAll("\\s+", " ");

        String cmd =
                command.toLowerCase(Locale.US);

        if (cmd.isEmpty()) {

            return new Result(
                    "",
                    false
            );
        }

        switch (cmd) {

            case "help":
            case "?":

                return out(help());

            case "rinxi":
            case "rinxi --full":
            case "full":

                return out(
                        probe.full()
                );

            case "system":
            case "sys":

                return out(
                        probe.system()
                );

            case "uname":

                return out(
                        probe.uname(false)
                );

            case "uname -a":

                return out(
                        probe.uname(true)
                );

            case "whoami":
            case "id":

                return out(
                        probe.whoami()
                );

            case "cpu":
            case "lscpu":

                return out(
                        probe.cpu()
                );

            case "free":
            case "free -h":

                return out(
                        probe.memory()
                );

            case "df":
            case "df -h":

                return out(
                        probe.storage()
                );

            case "display":

                return out(
                        probe.display()
                );

            case "battery":

                return out(
                        probe.battery()
                );

            case "net":
            case "network":
            case "ip":
            case "ip a":
            case "ip addr":

                return out(
                        probe.network()
                );

            case "sensors":

                return out(
                        probe.sensors()
                );

            case "security":

                return out(
                        probe.security()
                );

            case "json":
            case "rinxi --json":

                return out(
                        probe.json()
                );

            case "pwd":

                return out(
                        context.getFilesDir()
                                .getAbsolutePath()
                                + "\n"
                );

            case "clear":
            case "cls":

                return new Result(
                        "",
                        true
                );

            case "sudo":

                return out(
                        "sudo: unavailable\n"
                        + "RINXI runs inside the Android application sandbox.\n"
                        + "Root privileges were not requested.\n"
                );

            case "su":

                return out(
                        "su: unavailable\n"
                        + "[ANDROID SANDBOX]\n"
                );

            default:

                if (cmd.startsWith("rm ")
                        || cmd.startsWith("dd ")
                        || cmd.startsWith("chmod ")
                        || cmd.startsWith("sh ")
                        || cmd.startsWith("bash ")) {

                    return out(
                            "rinxi: command blocked\n\n"
                            + "[SAFE SHELL]\n"
                            + "RINXI v0.2 does not execute arbitrary shell commands.\n"
                            + "Type 'help' for supported commands.\n"
                    );
                }

                return out(
                        "rinxi: command not found: "
                                + command
                                + "\n"
                                + "Try: help\n"
                );
        }
    }

    private Result out(String text) {

        return new Result(
                text,
                false
        );
    }

    private String help() {

        return
                "\nRINXI SHELL COMMANDS\n"
              + "────────────────────────────────────\n"
              + "rinxi         full system probe\n"
              + "system        device + Android\n"
              + "uname -a      kernel information\n"
              + "whoami        app UID / sandbox\n"
              + "cpu / lscpu   processor information\n"
              + "free -h       memory status\n"
              + "df -h         storage status\n"
              + "display       display information\n"
              + "battery       battery status\n"
              + "net           network transport\n"
              + "sensors       detected sensors\n"
              + "security      security boundaries\n"
              + "json          structured report\n"
              + "pwd           application data path\n"
              + "clear         clear terminal\n"
              + "help          this help\n"
              + "\n"
              + "RINXI Shell is API-backed.\n"
              + "It is not an unrestricted Linux shell.\n";
    }
}
