package com.poc;

/** Shared page layout. Each repo has its own colour theme and label. */
final class Page {
    static final String LABEL = "Repo A";
    static final String COLOR = "#185FA5";
    static final String TINT = "#E6F1FB";

    private Page() {}

    static String wrap(String title, String bodyHtml) {
        return "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>" + LABEL + " - " + title + "</title></head>"
          + "<body style=\"margin:0;font-family:Arial,sans-serif;background:" + TINT + ";\">"
          + "<div style=\"max-width:360px;margin:60px auto;background:#fff;border-top:6px solid " + COLOR
          + ";border-radius:8px;padding:28px;box-shadow:0 1px 4px rgba(0,0,0,.15);\">"
          + "<span id=\"repo-badge\" style=\"background:" + COLOR + ";color:#fff;font-size:12px;padding:3px 10px;border-radius:10px;\">" + LABEL + "</span>"
          + bodyHtml + "</div></body></html>";
    }

    static String button(String id, String text) {
        return "<button type=\"submit\" id=\"" + id + "\" style=\"width:100%;padding:10px;background:" + COLOR
          + ";color:#fff;border:0;border-radius:6px;font-size:15px;cursor:pointer;\">" + text + "</button>";
    }
}
