import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import api from "../api/axiosConfig";
import "../styles/devices.css";

export default function Devices() {
  const role = localStorage.getItem("role");
  const isAdmin = role === "ADMIN";
  const isEngineer = role === "ENGINEER";
  const isAuditor = role === "AUDITOR";

  const [devices, setDevices] = useState([]);
  const [form, setForm] = useState({
    deviceId: "",
    type: "",
    ipAddress: "",
    location: "",
    status: "ACTIVE", 
  });
  const [editingId, setEditingId] = useState(null);

  useEffect(() => {
    loadDevices();
  }, []);

  const loadDevices = async () => {
    const res = await api.get("/api/devices");
    setDevices(res.data);
  };

  const handleSubmit = async () => {
    if (editingId) {
      const { deviceId, ...updatePayload } = form; // 
      await api.put(`/api/devices/${editingId}`, updatePayload);
    } else {
      await api.post("/api/devices", form);
    }
    resetForm();
    loadDevices();
  };

  const editDevice = (device) => {
    setEditingId(device.deviceId);
    setForm({
      deviceId: device.deviceId,
      type: device.type,
      ipAddress: device.ipAddress,
      location: device.location,
      status: device.status || "ACTIVE",
    });
  };

  const deleteDevice = async (id) => {
    await api.delete(`/api/devices/${id}`);
    loadDevices();
  };

  const resetForm = () => {
    setEditingId(null);
    setForm({
      deviceId: "",
      type: "",
      ipAddress: "",
      location: "",
      status: "ACTIVE",
    });
  };

  return (
    <>
      <Navbar />

      <div className="devices-container">
        <h2>Device Management</h2>

        {/* ADD / EDIT FORM */}
        {!isAuditor && (
          <div className="device-form">
            <input
              placeholder="Device ID"
              value={form.deviceId}
              onChange={(e) =>
                setForm({ ...form, deviceId: e.target.value })
              }
              disabled={!!editingId} 
            />

            <input
              placeholder="Type"
              value={form.type}
              onChange={(e) =>
                setForm({ ...form, type: e.target.value })
              }
            />

            <input
              placeholder="IP Address"
              value={form.ipAddress}
              onChange={(e) =>
                setForm({ ...form, ipAddress: e.target.value })
              }
            />

            <input
              placeholder="Location"
              value={form.location}
              onChange={(e) =>
                setForm({ ...form, location: e.target.value })
              }
            />

            {/* ✅ STATUS DROPDOWN */}
            <select
              value={form.status}
              onChange={(e) =>
                setForm({ ...form, status: e.target.value })
              }
            >
              <option value="ACTIVE">ACTIVE</option>
              <option value="OBSOLETE">OBSOLETE</option>
              <option value="MAINTENANCE">MAINTENANCE</option>
              <option value="DECOMMISSIONED">DECOMMISSIONED</option>
            </select>

            <button onClick={handleSubmit}>
              {editingId ? "Update Device" : "Add Device"}
            </button>
          </div>
        )}

        {/* TABLE */}
        <table className="device-table">
          <thead>
            <tr>
              <th>Device ID</th>
              <th>Type</th>
              <th>IP</th>
              <th>Location</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {devices.map((d) => (
              <tr key={d.deviceId}>
                <td>{d.deviceId}</td>
                <td>{d.type}</td>
                <td>{d.ipAddress}</td>
                <td>{d.location}</td>
                <td>{d.status || "UNKNOWN"}</td>

                <td>
                  {!isAuditor && (
                    <button onClick={() => editDevice(d)}>
                      Edit
                    </button>
                  )}

                  {isAdmin && (
                    <button
                      className="danger"
                      onClick={() => deleteDevice(d.deviceId)}
                    >
                      Delete
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
}
