package model;

import java.util.List;

public interface ReportGenerator<T> {
    String generateReport(List<T> data);
}