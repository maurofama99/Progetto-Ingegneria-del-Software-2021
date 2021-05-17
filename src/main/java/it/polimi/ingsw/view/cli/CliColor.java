package it.polimi.ingsw.view.cli;

public enum CliColor {

        ANSI_RED("\u001B[31m"),
        ANSI_GREEN("\u001B[32m"),
        ANSI_YELLOW("\u001B[33m"),
        ANSI_BLUE("\u001B[34m"),
        ANSI_PURPLE("\u001B[35m"),
        ANSI_GRAY("\u001B[90m");

        public static final String RESET = "\u001B[0m";

        private String escape;

        CliColor(String escape) { this.escape = escape;
        }
        public String escape() { return escape;
        }
}
