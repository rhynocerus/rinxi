package com.rhynus.rinxi;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;

import android.os.Build;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;

import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.rhynus.rinxi.shell.RinxiShell;
import com.rhynus.rinxi.ui.RinxiColors;

public class MainActivity extends Activity {

    private TextView terminal;
    private EditText commandInput;
    private ScrollView terminalScroll;

    private Typeface ubuntu;
    private Typeface ubuntuMono;

    private RinxiShell shell;

    private String prompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(
                RinxiColors.BG
        );

        getWindow().setNavigationBarColor(
                RinxiColors.BG
        );

        ubuntu =
                getResources()
                        .getFont(
                                R.font.ubuntu
                        );

        ubuntuMono =
                getResources()
                        .getFont(
                                R.font.ubuntu_mono
                        );

        shell =
                new RinxiShell(this);

        prompt =
                "rinxi@"
                + Build.MODEL
                + ":~$ ";

        createInterface();

        terminal.setText(
                "RINXI SYSTEM CONSOLE v0.2\n"
                + "LOCAL // NO ROOT // NO INTERNET\n"
                + "\n"
                + "Type 'help' or press FULL.\n"
                + "\n"
                + prompt
        );

        showIntro();
    }

    private void createInterface() {

        LinearLayout root =
                new LinearLayout(this);

        root.setOrientation(
                LinearLayout.VERTICAL
        );

        root.setBackgroundColor(
                RinxiColors.BG
        );

        root.setPadding(
                dp(14),
                dp(10),
                dp(14),
                dp(10)
        );

        root.setFitsSystemWindows(true);

        root.addView(
                createHeader()
        );

        root.addView(
                createCommandBar()
        );

        root.addView(
                createUtilityBar()
        );

        terminalScroll =
                new ScrollView(this);

        terminalScroll.setFillViewport(true);

        terminal =
                new TextView(this);

        terminal.setTypeface(
                ubuntuMono
        );

        terminal.setTextSize(12);

        terminal.setTextColor(
                RinxiColors.TEXT
        );

        terminal.setTextIsSelectable(true);

        terminal.setPadding(
                dp(12),
                dp(12),
                dp(12),
                dp(24)
        );

        terminal.setLineSpacing(
                0,
                1.08f
        );

        GradientDrawable terminalBg =
                new GradientDrawable();

        terminalBg.setColor(
                RinxiColors.PANEL
        );

        terminalBg.setStroke(
                dp(1),
                RinxiColors.GRID
        );

        terminal.setBackground(
                terminalBg
        );

        terminalScroll.addView(
                terminal,
                new ScrollView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );

        root.addView(
                terminalScroll,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0,
                        1
                )
        );

        root.addView(
                createInputBar()
        );

        setContentView(root);
    }

    private View createHeader() {

        LinearLayout header =
                new LinearLayout(this);

        header.setOrientation(
                LinearLayout.HORIZONTAL
        );

        header.setGravity(
                Gravity.CENTER_VERTICAL
        );

        LinearLayout titles =
                new LinearLayout(this);

        titles.setOrientation(
                LinearLayout.VERTICAL
        );

        TextView title =
                new TextView(this);

        title.setText("RINXI");

        title.setTypeface(
                ubuntu,
                Typeface.BOLD
        );

        title.setTextSize(29);

        title.setTextColor(
                RinxiColors.CYAN
        );

        TextView subtitle =
                new TextView(this);

        subtitle.setText(
                "SYSTEM PROBE // v0.2"
        );

        subtitle.setTypeface(
                ubuntu
        );

        subtitle.setTextSize(11);

        subtitle.setTextColor(
                RinxiColors.MUTED
        );

        titles.addView(title);
        titles.addView(subtitle);

        header.addView(
                titles,
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1
                )
        );

        TextView local =
                new TextView(this);

        local.setText("● LOCAL");

        local.setTypeface(
                ubuntuMono,
                Typeface.BOLD
        );

        local.setTextSize(11);

        local.setTextColor(
                RinxiColors.GREEN
        );

        header.addView(local);

        return header;
    }

    private View createCommandBar() {

        HorizontalScrollView hsv =
                new HorizontalScrollView(this);

        hsv.setHorizontalScrollBarEnabled(
                false
        );

        LinearLayout row =
                new LinearLayout(this);

        row.setOrientation(
                LinearLayout.HORIZONTAL
        );

        row.setPadding(
                0,
                dp(10),
                0,
                dp(5)
        );

        addCommandButton(
                row,
                "FULL",
                "rinxi"
        );

        addCommandButton(
                row,
                "SYS",
                "system"
        );

        addCommandButton(
                row,
                "CPU",
                "cpu"
        );

        addCommandButton(
                row,
                "MEM",
                "free -h"
        );

        addCommandButton(
                row,
                "DISK",
                "df -h"
        );

        addCommandButton(
                row,
                "NET",
                "net"
        );

        addCommandButton(
                row,
                "SEC",
                "security"
        );

        addCommandButton(
                row,
                "SNS",
                "sensors"
        );

        addCommandButton(
                row,
                "JSON",
                "json"
        );

        hsv.addView(row);

        return hsv;
    }

    private View createUtilityBar() {

        LinearLayout row =
                new LinearLayout(this);

        row.setOrientation(
                LinearLayout.HORIZONTAL
        );

        row.setPadding(
                0,
                0,
                0,
                dp(6)
        );

        Button copy =
                utilityButton("COPY");

        copy.setOnClickListener(
                v -> copyTerminal()
        );

        Button share =
                utilityButton("SHARE");

        share.setOnClickListener(
                v -> shareTerminal()
        );

        Button help =
                utilityButton("HELP");

        help.setOnClickListener(
                v -> executeCommand("help")
        );

        Button clear =
                utilityButton("CLEAR");

        clear.setOnClickListener(
                v -> executeCommand("clear")
        );

        row.addView(copy);
        row.addView(share);
        row.addView(help);
        row.addView(clear);

        return row;
    }

    private View createInputBar() {

        LinearLayout row =
                new LinearLayout(this);

        row.setOrientation(
                LinearLayout.HORIZONTAL
        );

        row.setGravity(
                Gravity.CENTER_VERTICAL
        );

        row.setPadding(
                0,
                dp(8),
                0,
                0
        );

        TextView promptView =
                new TextView(this);

        promptView.setText("$");

        promptView.setTypeface(
                ubuntuMono,
                Typeface.BOLD
        );

        promptView.setTextSize(15);

        promptView.setTextColor(
                RinxiColors.GREEN
        );

        row.addView(promptView);

        commandInput =
                new EditText(this);

        commandInput.setSingleLine(true);

        commandInput.setTypeface(
                ubuntuMono
        );

        commandInput.setTextSize(14);

        commandInput.setTextColor(
                RinxiColors.TEXT
        );

        commandInput.setHintTextColor(
                RinxiColors.MUTED
        );

        commandInput.setHint(
                " command"
        );

        commandInput.setBackgroundColor(
                RinxiColors.PANEL_ALT
        );

        commandInput.setPadding(
                dp(8),
                dp(7),
                dp(8),
                dp(7)
        );

        commandInput.setImeOptions(
                EditorInfo.IME_ACTION_GO
        );

        commandInput.setOnEditorActionListener(
                (v, actionId, event) -> {

                    if (
                            actionId
                                    == EditorInfo.IME_ACTION_GO
                    ) {

                        executeTypedCommand();

                        return true;
                    }

                    return false;
                }
        );

        row.addView(
                commandInput,
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1
                )
        );

        Button exec =
                utilityButton("EXEC");

        exec.setTextColor(
                RinxiColors.CYAN
        );

        exec.setOnClickListener(
                v -> executeTypedCommand()
        );

        row.addView(exec);

        return row;
    }

    private void addCommandButton(
            LinearLayout row,
            String label,
            String command
    ) {

        Button button =
                new Button(this);

        button.setText(label);

        button.setAllCaps(false);

        button.setTypeface(
                ubuntuMono,
                Typeface.BOLD
        );

        button.setTextSize(11);

        button.setTextColor(
                RinxiColors.CYAN
        );

        button.setMinHeight(0);
        button.setMinWidth(0);

        button.setPadding(
                dp(12),
                dp(7),
                dp(12),
                dp(7)
        );

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(
                RinxiColors.PANEL
        );

        bg.setStroke(
                dp(1),
                RinxiColors.GRID
        );

        bg.setCornerRadius(
                dp(2)
        );

        button.setBackground(bg);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(
                0,
                0,
                dp(5),
                0
        );

        button.setLayoutParams(params);

        button.setOnClickListener(
                v -> executeCommand(command)
        );

        row.addView(button);
    }

    private Button utilityButton(
            String label
    ) {

        Button button =
                new Button(this);

        button.setText(label);

        button.setAllCaps(false);

        button.setTypeface(
                ubuntuMono
        );

        button.setTextSize(10);

        button.setTextColor(
                RinxiColors.MUTED
        );

        button.setMinHeight(0);
        button.setMinWidth(0);

        button.setPadding(
                dp(9),
                dp(4),
                dp(9),
                dp(4)
        );

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(
                RinxiColors.PANEL_ALT
        );

        bg.setStroke(
                dp(1),
                RinxiColors.GRID
        );

        button.setBackground(bg);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1
                );

        params.setMargins(
                0,
                0,
                dp(4),
                0
        );

        button.setLayoutParams(params);

        return button;
    }

    private void executeTypedCommand() {

        String command =
                commandInput.getText()
                        .toString();

        commandInput.setText("");

        executeCommand(command);
    }

    private void executeCommand(
            String command
    ) {

        if (
                command == null
                || command.trim().isEmpty()
        ) {

            return;
        }

        RinxiShell.Result result =
                shell.execute(command);

        if (result.clear) {

            terminal.setText(
                    prompt
            );

            return;
        }

        String current =
                terminal.getText()
                        .toString();

        if (
                !current.endsWith(prompt)
        ) {

            current += "\n" + prompt;
        }

        current += command + "\n";

        current += result.text;

        if (!current.endsWith("\n")) {
            current += "\n";
        }

        current += "\n" + prompt;

        terminal.setText(current);

        scrollToBottom();
    }

    private void scrollToBottom() {

        terminalScroll.post(
                () -> terminalScroll.fullScroll(
                        View.FOCUS_DOWN
                )
        );
    }

    private void copyTerminal() {

        ClipboardManager clipboard =
                (ClipboardManager)
                        getSystemService(
                                Context.CLIPBOARD_SERVICE
                        );

        clipboard.setPrimaryClip(
                ClipData.newPlainText(
                        "RINXI terminal",
                        terminal.getText()
                )
        );

        Toast.makeText(
                this,
                "RINXI output copied",
                Toast.LENGTH_SHORT
        ).show();
    }

    private void shareTerminal() {

        Intent share =
                new Intent(
                        Intent.ACTION_SEND
                );

        share.setType(
                "text/plain"
        );

        share.putExtra(
                Intent.EXTRA_SUBJECT,
                "RINXI system report"
        );

        share.putExtra(
                Intent.EXTRA_TEXT,
                terminal.getText()
                        .toString()
        );

        startActivity(
                Intent.createChooser(
                        share,
                        "Share RINXI output"
                )
        );
    }

    private void showIntro() {

        new AlertDialog.Builder(this)
                .setTitle(
                        "RINXI // LOCAL PROBE"
                )
                .setMessage(
                        "System information is collected locally.\n\n"
                        + "NO ROOT\n"
                        + "NO INTERNET PERMISSION\n"
                        + "API-BACKED SAFE SHELL\n\n"
                        + "Restricted information is reported, "
                        + "never guessed."
                )
                .setNegativeButton(
                        "TERMINAL",
                        null
                )
                .setPositiveButton(
                        "FULL PROBE",
                        (dialog, which) ->
                                executeCommand(
                                        "rinxi"
                                )
                )
                .show();
    }

    private int dp(int value) {

        return (int) (
                value
                        * getResources()
                        .getDisplayMetrics()
                        .density
        );
    }
}
