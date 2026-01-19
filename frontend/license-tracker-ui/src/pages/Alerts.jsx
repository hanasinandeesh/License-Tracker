import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import api from "../api/axiosConfig";
import "../styles/alerts.css";

export default function Alerts() {
  const [licenseAlerts, setLicenseAlerts] = useState([]);
  const [deviceAlerts, setDeviceAlerts] = useState([]);
  const [tab, setTab] = useState("licenses");

  const [selectedLicense, setSelectedLicense] = useState("");
  const [message, setMessage] = useState("");

  useEffect(() => {
    loadAlerts();
  }, []);

  const loadAlerts = async () => {
    const [l, d] = await Promise.all([
      api.get("/api/alerts/licenses?days=30"),
      api.get("/api/alerts/devices?days=30"),
    ]);

    setLicenseAlerts(l.data);
    setDeviceAlerts(d.data);
  };

  const sendReminder = () => {
    const lic = licenseAlerts.find(
      (l) => l.licenseKey === selectedLicense
    );

    if (!lic) return;

    setMessage(
      `📧 Reminder sent: License ${lic.licenseKey} expires in ${lic.remainingDays} days`
    );

    setTimeout(() => setMessage(""), 4000);
  };

  const renderAlert = (level, days) => {
    switch (level) {
      case "EXPIRED":
        return "🔴 Expired";
      case "CRITICAL":
        return `🔴 ${days} days left`;
      case "WARNING":
        return `🟡 ${days} days left`;
      default:
        return "🟢 Valid";
    }
  };

  return (
    <>
      <Navbar />

      <div className="alerts-page">
        <div className="alerts-header">
          <div className="tabs">
            <button
              className={tab === "licenses" ? "active" : ""}
              onClick={() => setTab("licenses")}
            >
              License Alerts
            </button>

            <button
              className={tab === "devices" ? "active" : ""}
              onClick={() => setTab("devices")}
            >
              Device Impact
            </button>
          </div>

          <div className="reminder-box">
            <select
              value={selectedLicense}
              onChange={(e) => setSelectedLicense(e.target.value)}
            >
              <option value="">Select expiring license</option>
              {licenseAlerts
                .filter(
                  (l) =>
                    l.alertLevel === "CRITICAL" ||
                    l.alertLevel === "WARNING"
                )
                .map((l) => (
                  <option key={l.licenseKey} value={l.licenseKey}>
                    {l.licenseKey} ({l.remainingDays} days)
                  </option>
                ))}
            </select>

            <button
              disabled={!selectedLicense}
              onClick={sendReminder}
            >
              Send Reminder
            </button>
          </div>
        </div>

        {message && <div className="alert-msg">{message}</div>}

        {tab === "licenses" && (
          <table>
            <thead>
              <tr>
                <th>License Key</th>
                <th>Software</th>
                <th>Expiry Date</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {licenseAlerts.map((l) => (
                <tr key={l.licenseKey}>
                  <td>{l.licenseKey}</td>
                  <td>{l.softwareName}</td>
                  <td>{l.validTo}</td>
                  <td>{renderAlert(l.alertLevel, l.remainingDays)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}

        {tab === "devices" && (
          <table>
            <thead>
              <tr>
                <th>Device ID</th>
                <th>License Key</th>
                <th>Impact</th>
              </tr>
            </thead>
            <tbody>
              {deviceAlerts.map((d, i) => (
                <tr key={i}>
                  <td>{d.deviceId}</td>
                  <td>{d.licenseKey}</td>
                  <td>⚠️ License Expiring</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </>
  );
}
