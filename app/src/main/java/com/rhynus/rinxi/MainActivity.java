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
import android.widget.ImageView;
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
                dp(10),
                dp(6),
                dp(10),
                dp(6)
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
                dp(10),
                dp(10),
                dp(10),
                dp(20)
        );

        terminal.setLineSpacing(
                0,
                1.06f
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

        LinearLayout wrap =
                new LinearLayout(this);

        wrap.setOrientation(
                LinearLayout.VERTICAL
        );

        /*
         * Primera línea:
         *
         * [ LOGO RINXI ] [       LOGO RIW OFICIAL       ]
         *
         * Conservamos el isotipo de RINXI.
         * Eliminamos solamente los textos redundantes.
         */
        LinearLayout logos =
                new LinearLayout(this);

        logos.setOrientation(
                LinearLayout.HORIZONTAL
        );

        logos.setGravity(
                Gravity.CENTER_VERTICAL
        );

        ImageView rinxiLogo =
                new ImageView(this);

        rinxiLogo.setImageResource(
                R.drawable.rinxi_monogram
        );

        rinxiLogo.setContentDescription(
                "RINXI"
        );

        rinxiLogo.setAdjustViewBounds(true);

        rinxiLogo.setScaleType(
                ImageView.ScaleType.FIT_CENTER
        );

        LinearLayout.LayoutParams rinxiParams =
                new LinearLayout.LayoutParams(
                        dp(48),
                        dp(48)
                );

        rinxiParams.setMargins(
                0,
                0,
                dp(8),
                0
        );

        logos.addView(
                rinxiLogo,
                rinxiParams
        );

        /*
         * RIW ocupa TODO el ancho restante.
         * Así gana mucha presencia sin deformarse
         * ni salirse de la pantalla.
         */
        ImageView riwLogo =
                new ImageView(this);

        riwLogo.setImageResource(
                R.drawable.riw_logo_autorizado
        );

        riwLogo.setContentDescription(
                "RHYNUS Interactive Works"
        );

        riwLogo.setAdjustViewBounds(true);

        riwLogo.setScaleType(
                ImageView.ScaleType.FIT_END
        );

        LinearLayout.LayoutParams riwParams =
                new LinearLayout.LayoutParams(
                        0,
                        dp(122),
                        1
                );

        logos.addView(
                riwLogo,
                riwParams
        );

        wrap.addView(logos);

        /*
         * Segunda línea compacta:
         *
         * SYSTEM PROBE // v0.2              ● LOCAL
         */
        LinearLayout statusRow =
                new LinearLayout(this);

        statusRow.setOrientation(
                LinearLayout.HORIZONTAL
        );

        statusRow.setGravity(
                Gravity.CENTER_VERTICAL
        );

        TextView status =
                new TextView(this);

        status.setText(
                "SYSTEM PROBE // v0.2"
        );

        status.setTypeface(
                ubuntuMono
        );

        status.setTextSize(8);

        status.setLetterSpacing(
                0.04f
        );

        status.setTextColor(
                RinxiColors.MUTED
        );

        statusRow.addView(
                status,
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

        local.setTextSize(9);

        local.setTextColor(
                RinxiColors.GREEN
        );

        statusRow.addView(local);

        wrap.addView(statusRow);

        return wrap;
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

        row.setGravity(
                Gravity.CENTER_VERTICAL
        );

        row.setPadding(
                0,
                dp(4),
                0,
                dp(2)
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

        row.setGravity(
                Gravity.START
        );

        row.setPadding(
                0,
                0,
                0,
                dp(3)
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
                dp(5),
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

        promptView.setTextSize(14);

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

        commandInput.setTextSize(13);

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
                dp(7),
                dp(4),
                dp(7),
                dp(4)
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
                        dp(34),
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

    /*
     * Botones de comandos:
     * FULL SYS CPU MEM DISK NET SEC SNS JSON
     *
     * Los hacemos realmente compactos:
     * - texto 9sp
     * - altura fija 25dp
     * - padding mínimo
     * - separación 2dp
     */
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

        button.setTextSize(9);

        button.setTextColor(
                RinxiColors.CYAN
        );

        button.setMinHeight(0);
        button.setMinimumHeight(0);

        button.setMinWidth(0);
        button.setMinimumWidth(0);

        button.setIncludeFontPadding(false);

        button.setGravity(
                Gravity.CENTER
        );

        button.setPadding(
                dp(6),
                0,
                dp(6),
                0
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
                        dp(25)
                );

        params.setMargins(
                0,
                0,
                dp(2),
                0
        );

        button.setLayoutParams(params);

        button.setOnClickListener(
                v -> executeCommand(command)
        );

        row.addView(button);
    }

    /*
     * COPY SHARE HELP CLEAR y EXEC
     *
     * Más compactos todavía.
     */
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

        button.setTextSize(8);

        button.setTextColor(
                RinxiColors.MUTED
        );

        button.setMinHeight(0);
        button.setMinimumHeight(0);

        button.setMinWidth(0);
        button.setMinimumWidth(0);

        button.setIncludeFontPadding(false);

        button.setGravity(
                Gravity.CENTER
        );

        button.setPadding(
                dp(6),
                0,
                dp(6),
                0
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

        bg.setCornerRadius(
                dp(2)
        );

        button.setBackground(bg);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        dp(23)
                );

        params.setMargins(
                0,
                0,
                dp(3),
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
                        "RINXI // SONDEO LOCAL"
                )
                .setMessage(
                        "La información del sistema se recopila localmente.\n\n"
                                + "SIN ROOT\n"
                                + "SIN PERMISO DE INTERNET\n"
                                + "CONSOLA SEGURA BASADA EN API\n\n"
                                + "La información restringida se indica como tal; "
                                + "nunca se inventa."
                )
                .setNegativeButton(
                        "TERMINAL",
                        null
                )
                .setPositiveButton(
                        "SONDEO COMPLETO",
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
