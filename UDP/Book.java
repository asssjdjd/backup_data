package UDP;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Book implements Serializable {
    private static final long serialVersionUID = 20251107L;

    private String id;
    private String title;
    private String author;
    private String isbn;
    private String publishDate;

    public Book() {}

    public Book(String id, String title, String author, String isbn, String publishDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
    }

    // getters / setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getPublishDate() { return publishDate; }
    public void setPublishDate(String publishDate) { this.publishDate = publishDate; }

    // normalize all fields as required
    public void normalize() {
        this.title = normalizeTitle(this.title);
        this.author = normalizeAuthor(this.author);
        this.isbn = normalizeIsbn(this.isbn);
        this.publishDate = normalizePublishDate(this.publishDate);
    }

    private String normalizeTitle(String input) {
        if (input == null || input.isEmpty()) return input;
        String[] words = input.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (w.length() == 0) continue;
            sb.append(Character.toUpperCase(w.charAt(0)));
            if (w.length() > 1) sb.append(w.substring(1));
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    // "Họ, Tên" — lấy token đầu làm họ, phần còn lại là tên
   // Author: "Họ Đệm, Tên"
    private String normalizeAuthor(String input) {
        if (input == null || input.isEmpty()) return input;
        String[] parts = input.trim().split("\\s+");
        if (parts.length == 1) return parts[0]; // chỉ có 1 từ
        StringBuilder firstPart = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) {
            firstPart.append(parts[i]);
            if (i < parts.length - 2) firstPart.append(' ');
        }
        String lastName = parts[parts.length - 1];
        return firstPart.toString() + ", " + lastName;
    }


    // Định dạng cố định 13-digit -> 978-3-16-148410-0 (nhóm 3-1-2-6-1 theo yêu cầu)
    private String normalizeIsbn(String input) {
        if (input == null) return null;
        String digits = input.replaceAll("[^0-9]", "");
        if (digits.length() != 13) return input; // giữ nguyên nếu không đúng 13 chữ số
        return digits.substring(0,3) + "-" +
               digits.charAt(3) + "-" +
               digits.substring(4,6) + "-" +
               digits.substring(6,12) + "-" +
               digits.charAt(12);
    }

    // yyyy-MM-dd -> MM/yyyy
    private String normalizePublishDate(String input) {
        if (input == null) return null;
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse(input);
            return new SimpleDateFormat("MM/yyyy").format(d);
        } catch (ParseException e) {
            return input;
        }
    }

    @Override
    public String toString() {
        return "Book{" +
               "id='" + id + '\'' +
               ", title='" + title + '\'' +
               ", author='" + author + '\'' +
               ", isbn='" + isbn + '\'' +
               ", publishDate='" + publishDate + '\'' +
               '}';
    }
}
