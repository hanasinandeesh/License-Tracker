import { useEffect, useState } from "react";
import api from "../api/axiosConfig";

export default function CsvTable({ url, title }) {
  const [headers, setHeaders] = useState([]);
  const [rows, setRows] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadCsv();
  }, [url]);

  const loadCsv = async () => {
    try {
      setLoading(true);

      const res = await api.get(url, {
        responseType: "text",
      });

      parseCsv(res.data);
    } catch (err) {
      console.error("Failed to load CSV", err);
      alert("Failed to load report");
    } finally {
      setLoading(false);
    }
  };

  const parseCsv = (csv) => {
    const lines = csv.trim().split("\n");
    if (lines.length === 0) return;

    const headerLine = lines[0].split(",");
    const dataLines = lines.slice(1);

    const tableRows = dataLines.map((line) => line.split(","));

    setHeaders(headerLine);
    setRows(tableRows);
  };

  return (
    <div className="csv-card">
      <h3>{title}</h3>

      {loading && <p>Loading...</p>}

      {!loading && (
        <table className="csv-table">
          <thead>
            <tr>
              {headers.map((h, i) => (
                <th key={i}>{h}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {rows.map((row, rIdx) => (
              <tr key={rIdx}>
                {row.map((cell, cIdx) => (
                  <td key={cIdx}>{cell}</td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
