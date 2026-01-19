import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import api from "../api/axiosConfig";
import "../styles/lifecycle.css";

const LIFECYCLE_STAGES = [
  "ACTIVE",
  "MAINTENANCE",
  "OBSOLETE",
  "DECOMMISSIONED",
];

const SOFTWARE_STATUS = [
  "UP_TO_DATE",
  "OUTDATED",
  "CRITICAL",
];

export default function Lifecycle() {
  const role = localStorage.getItem("role");
  const isAdmin = role === "ADMIN";

  const [rows, setRows] = useState([]);
  const [editRowId, setEditRowId] = useState(null);
  const [editData, setEditData] = useState({});
  const [showAdd, setShowAdd] = useState(false);

  const [newRow, setNewRow] = useState({
    deviceId: "",
    softwareName: "",
    currentVersion: "",
    latestVersion: "",
    status: "UP_TO_DATE",
    lifecycleStage: "ACTIVE",
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    const res = await api.get("/api/software");
    setRows(res.data || []);
  };

  const startEdit = (row) => {
    setEditRowId(row.svId);
    setEditData({ ...row });
  };

  const cancelEdit = () => {
    setEditRowId(null);
    setEditData({});
  };

  const saveEdit = async () => {
    await api.put(`/api/software/${editRowId}`, editData);
    cancelEdit();
    loadData();
  };

  const deleteRow = async (svId) => {
    if (!window.confirm("Delete this software record?")) return;
    await api.delete(`/api/software/${svId}`);
    loadData();
  };

  const addRow = async () => {
    await api.post("/api/software", newRow);
    setShowAdd(false);
    setNewRow({
      deviceId: "",
      softwareName: "",
      currentVersion: "",
      latestVersion: "",
      status: "UP_TO_DATE",
      lifecycleStage: "ACTIVE",
    });
    loadData();
  };

  return (
    <>
      <Navbar />

      <div className="page-container">
        <h2>Software Lifecycle & Version Management</h2>

        {/* ✅ ADMIN ONLY */}
        {isAdmin && (
          <button
            className="primary-btn"
            onClick={() => setShowAdd(!showAdd)}
          >
            + Add Software
          </button>
        )}

        {/* ================= ADD FORM ================= */}
        {isAdmin && showAdd && (
          <div className="add-form">
            <input
              placeholder="Device ID"
              value={newRow.deviceId}
              onChange={(e) =>
                setNewRow({ ...newRow, deviceId: e.target.value })
              }
            />
            <input
              placeholder="Installed Software"
              value={newRow.softwareName}
              onChange={(e) =>
                setNewRow({ ...newRow, softwareName: e.target.value })
              }
            />
            <input
              placeholder="Installed Version"
              value={newRow.currentVersion}
              onChange={(e) =>
                setNewRow({ ...newRow, currentVersion: e.target.value })
              }
            />
            <input
              placeholder="Latest Vendor Version"
              value={newRow.latestVersion}
              onChange={(e) =>
                setNewRow({ ...newRow, latestVersion: e.target.value })
              }
            />

            <select
              value={newRow.status}
              onChange={(e) =>
                setNewRow({ ...newRow, status: e.target.value })
              }
            >
              {SOFTWARE_STATUS.map((s) => (
                <option key={s}>{s}</option>
              ))}
            </select>

            <select
              value={newRow.lifecycleStage}
              onChange={(e) =>
                setNewRow({ ...newRow, lifecycleStage: e.target.value })
              }
            >
              {LIFECYCLE_STAGES.map((s) => (
                <option key={s}>{s}</option>
              ))}
            </select>

            <button onClick={addRow}>Save</button>
          </div>
        )}

        {/* ================= TABLE ================= */}
        <table className="data-table">
          <thead>
            <tr>
              <th>Device ID</th>
              <th>Installed Software</th>
              <th>Installed Version</th>
              <th>Latest Version</th>
              <th>Status</th>
              <th>Lifecycle Stage</th>
              {isAdmin && <th>Actions</th>}
            </tr>
          </thead>

          <tbody>
            {rows.map((row) => (
              <tr key={row.svId}>
                <td>{row.deviceId}</td>
                <td>{row.softwareName}</td>
                <td>{row.currentVersion}</td>
                <td>{row.latestVersion}</td>
                <td>{row.status}</td>
                <td>{row.lifecycleStage}</td>

                {isAdmin && (
                  <td>
                    <button onClick={() => startEdit(row)}>Edit</button>
                    <button
                      className="danger"
                      onClick={() => deleteRow(row.svId)}
                    >
                      Delete
                    </button>
                  </td>
                )}
              </tr>
            ))}
          </tbody>
        </table>

        {/* ================= EDIT MODAL ================= */}
        {isAdmin && editRowId && (
          <div className="modal">
            <div className="modal-content">
              <h3>Edit Software</h3>

              <input
                value={editData.softwareName}
                onChange={(e) =>
                  setEditData({
                    ...editData,
                    softwareName: e.target.value,
                  })
                }
              />
              <input
                value={editData.currentVersion}
                onChange={(e) =>
                  setEditData({
                    ...editData,
                    currentVersion: e.target.value,
                  })
                }
              />
              <input
                value={editData.latestVersion}
                onChange={(e) =>
                  setEditData({
                    ...editData,
                    latestVersion: e.target.value,
                  })
                }
              />

              <select
                value={editData.status}
                onChange={(e) =>
                  setEditData({ ...editData, status: e.target.value })
                }
              >
                {SOFTWARE_STATUS.map((s) => (
                  <option key={s}>{s}</option>
                ))}
              </select>

              <select
                value={editData.lifecycleStage}
                onChange={(e) =>
                  setEditData({
                    ...editData,
                    lifecycleStage: e.target.value,
                  })
                }
              >
                {LIFECYCLE_STAGES.map((s) => (
                  <option key={s}>{s}</option>
                ))}
              </select>

              <div className="form-actions">
                <button onClick={saveEdit}>Save</button>
                <button onClick={cancelEdit}>Cancel</button>
              </div>
            </div>
          </div>
        )}
      </div>
    </>
  );
}
