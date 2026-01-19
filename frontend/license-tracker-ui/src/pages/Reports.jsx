import { useState } from "react";
import Navbar from "../components/Navbar";
import api from "../api/axiosConfig";
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";
import "../styles/reports.css";

export default function Reports() {
  const role = localStorage.getItem("role");
  const [preview, setPreview] = useState(null);

  // =========================
  // FETCH CSV (CLEAN VERSION)
  // =========================
  const fetchCsv = async (endpoint) => {
    const res = await api.get(endpoint, {
      responseType: "text",
      validateStatus: () => true,
    });

    // ✅ Only validate using HTTP status
    if (res.status !== 200) {
      throw new Error("Invalid CSV response");
    }

    const lines = res.data.trim().split("\n");

    const headers = lines[0]
      .split(",")
      .map((h) => h.replace(/"/g, "").trim());

    const rows = lines.slice(1).map((r) =>
      r.split(",").map((c) => c.replace(/"/g, "").trim())
    );

    return { headers, rows };
  };

  // =========================
  // VIEW REPORT
  // =========================
  const viewReport = async (endpoint, title) => {
    try {
      const data = await fetchCsv(endpoint);
      setPreview({ title, ...data });
    } catch (err) {
      alert("Failed to load report preview");
      console.error(err);
    }
  };

  // =========================
  // DOWNLOAD PDF
  // =========================
  const downloadPdf = async (endpoint, title) => {
    try {
      const { headers, rows } = await fetchCsv(endpoint);

      const doc = new jsPDF({
        orientation: "landscape",
        unit: "pt",
        format: "a4",
      });

      doc.setFontSize(14);
      doc.text(title, 40, 30);

      autoTable(doc, {
        head: [headers],
        body: rows,
        startY: 50,
        styles: {
          fontSize: 8,
          cellPadding: 4,
        },
        headStyles: {
          fillColor: [22, 160, 133],
        },
      });

      doc.save(`${title.replace(/\s+/g, "_")}.pdf`);
    } catch (err) {
      alert("PDF download failed");
      console.error(err);
    }
  };

  return (
    <>
      <Navbar />

      <div className="reports-page">
        <h2>Reports</h2>

        {/* LICENSE REPORT */}
        <div className="report-card">
          <h3>License Usage Report</h3>
          <div className="btn-group">
            <button
              onClick={() =>
                viewReport("/api/reports/licenses/csv", "License Usage Report")
              }
            >
              View
            </button>
            <button
              onClick={() =>
                downloadPdf(
                  "/api/reports/licenses/csv",
                  "License Usage Report"
                )
              }
            >
              Download PDF
            </button>
          </div>
        </div>

        {/* NON-COMPLIANT DEVICES */}
        <div className="report-card">
          <h3>Non-Compliant Devices</h3>
          <div className="btn-group">
            <button
              onClick={() =>
                viewReport(
                  "/api/reports/devices/non-compliant/csv",
                  "Non-Compliant Devices"
                )
              }
            >
              View
            </button>
            <button
              onClick={() =>
                downloadPdf(
                  "/api/reports/devices/non-compliant/csv",
                  "Non-Compliant Devices"
                )
              }
            >
              Download PDF
            </button>
          </div>
        </div>

        {/* AUDIT REPORT */}
        <div className="report-card">
          <h3>Audit Report</h3>
          <div className="btn-group">
            <button
              onClick={() =>
                viewReport("/api/reports/audit/csv", "Audit Report")
              }
            >
              View
            </button>
            <button
              onClick={() =>
                downloadPdf("/api/reports/audit/csv", "Audit Report")
              }
            >
              Download PDF
            </button>
          </div>
        </div>

        {/* AUDIT LOGS */}
        <div className="report-card">
          <h3>Audit Logs</h3>
          <div className="btn-group">
            <button
              onClick={() =>
                viewReport("/api/audit-logs/csv", "Audit Logs")
              }
            >
              View
            </button>
            <button
              onClick={() =>
                downloadPdf("/api/audit-logs/csv", "Audit Logs")
              }
            >
              Download PDF
            </button>
          </div>
        </div>

        {/* PREVIEW */}
        {preview && (
          <div className="preview-section">
            <h3>{preview.title}</h3>

            <table>
              <thead>
                <tr>
                  {preview.headers.map((h, i) => (
                    <th key={i}>{h}</th>
                  ))}
                </tr>
              </thead>
              <tbody>
                {preview.rows.map((r, i) => (
                  <tr key={i}>
                    {r.map((c, j) => (
                      <td key={j}>{c}</td>
                    ))}
                  </tr>
                ))}
              </tbody>
            </table>

            <button onClick={() => setPreview(null)}>Close</button>
          </div>
        )}
      </div>
    </>
  );
}
