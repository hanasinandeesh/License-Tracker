import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import api from "../api/axiosConfig";
import "../styles/assignments.css";

export default function Assignments() {
  const role = localStorage.getItem("role");
  const isAuditor = role === "AUDITOR";

  const [devices, setDevices] = useState([]);
  const [licenses, setLicenses] = useState([]);
  const [assignments, setAssignments] = useState([]);

  const [deviceId, setDeviceId] = useState("");
  const [licenseKey, setLicenseKey] = useState("");

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    const [dRes, lRes, aRes] = await Promise.all([
      api.get("/api/devices"),
      api.get("/api/licenses"),
      api.get("/api/assignments"),
    ]);

    setDevices(dRes.data || []);
    setLicenses(lRes.data || []);
    setAssignments(aRes.data || []);
  };

  const assignLicense = async () => {
    await api.post("/api/assignments", {
      deviceId,
      licenseKey,
    });
    setDeviceId("");
    setLicenseKey("");
    loadData();
  };

  const revokeAssignment = async (id) => {
    await api.delete(`/api/assignments/${id}`);
    loadData();
  };

  return (
    <>
      <Navbar />

      <div className="page-container">
        <h2>License Assignments</h2>

        {/* ASSIGN FORM */}
        <div className="assign-form">
          <select
            value={deviceId}
            onChange={(e) => setDeviceId(e.target.value)}
            disabled={isAuditor}
          >
            <option value="">Select Device</option>
            {devices.map((d) => (
              <option key={d.deviceId} value={d.deviceId}>
                {d.deviceId}
              </option>
            ))}
          </select>

          <select
            value={licenseKey}
            onChange={(e) => setLicenseKey(e.target.value)}
            disabled={isAuditor}
          >
            <option value="">Select License</option>
            {licenses.map((l) => (
              <option key={l.licenseKey} value={l.licenseKey}>
                {l.licenseKey}
              </option>
            ))}
          </select>

          {!isAuditor && (
            <button className="primary-btn" onClick={assignLicense}>
              Assign
            </button>
          )}
        </div>

        {/* TABLE */}
        <table className="data-table">
          <thead>
            <tr>
              <th>Device ID</th>
              <th>License Key</th>
              <th>Assigned On</th>
              {!isAuditor && <th>Actions</th>}
            </tr>
          </thead>

          <tbody>
            {assignments.length === 0 ? (
              <tr>
                <td colSpan={isAuditor ? 3 : 4} className="empty">
                  No assignments found
                </td>
              </tr>
            ) : (
              assignments.map((a) => (
                <tr key={a.assignmentId}>
                  <td>{a.deviceId}</td>
                  <td>{a.licenseKey}</td>
                  <td>{a.assignedOn}</td>

                  {!isAuditor && (
                    <td>
                      <button
                        className="danger"
                        onClick={() => revokeAssignment(a.assignmentId)}
                      >
                        Revoke
                      </button>
                    </td>
                  )}
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </>
  );
}

