import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import api from "../api/axiosConfig";
import "../styles/licenses.css";

export default function Licenses() {
  const role = localStorage.getItem("role");
  const isAdmin = role === "ADMIN";

  const [licenses, setLicenses] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [editing, setEditing] = useState(false);

  const [formData, setFormData] = useState({
    licenseKey: "",
    softwareName: "",
    validFrom: "",
    validTo: "",
    maxUsage: "",
    vendor: { vendorId: 1 } 
  });

  useEffect(() => {
    fetchLicenses();
  }, []);

  const fetchLicenses = async () => {
    const res = await api.get("/api/licenses");
    setLicenses(res.data || []);
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const openAddForm = () => {
    setEditing(false);
    setFormData({
      licenseKey: "",
      softwareName: "",
      validFrom: "",
      validTo: "",
      maxUsage: "",
      vendor: { vendorId: 1 } 
    });
    setShowForm(true);
  };

  const openEditForm = (l) => {
    setEditing(true);

    setFormData({
      licenseKey: l.licenseKey,
      softwareName: l.softwareName,
      validFrom: l.validFrom,
      validTo: l.validTo,
      maxUsage: l.maxUsage,
      vendor: { vendorId: l.vendor?.vendorId || 1 }
    });

    setShowForm(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (editing) {
      await api.put(`/api/licenses/${formData.licenseKey}`, formData);
    } else {
      await api.post("/api/licenses", formData);
    }

    setShowForm(false);
    fetchLicenses();
  };

  const handleDelete = async (key) => {
    await api.delete(`/api/licenses/${key}`);
    fetchLicenses();
  };

  return (
    <>
      <Navbar />

      <div className="page-container">
        <div className="page-header">
          <h2>License Management</h2>

          {isAdmin && (
            <button className="primary-btn" onClick={openAddForm}>
              + Add License
            </button>
          )}
        </div>

        {showForm && isAdmin && (
          <form className="data-form" onSubmit={handleSubmit}>
            <input
              name="licenseKey"
              placeholder="License Key"
              value={formData.licenseKey}
              onChange={handleChange}
              disabled={editing}
              required
            />

            <input
              name="softwareName"
              placeholder="Software Name"
              value={formData.softwareName}
              onChange={handleChange}
              required
            />

            <input
              type="date"
              name="validFrom"
              value={formData.validFrom}
              onChange={handleChange}
              required
            />

            <input
              type="date"
              name="validTo"
              value={formData.validTo}
              onChange={handleChange}
              required
            />

            <input
              type="number"
              name="maxUsage"
              placeholder="Max Usage"
              value={formData.maxUsage}
              onChange={handleChange}
              required
            />

            <button type="submit">
              {editing ? "Update" : "Save"}
            </button>

            <button type="button" onClick={() => setShowForm(false)}>
              Cancel
            </button>
          </form>
        )}

        <table className="data-table">
          <thead>
            <tr>
              <th>License Key</th>
              <th>Software</th>
              <th>Valid From</th>
              <th>Expiry Date</th>
              <th>Max Usage</th>
              {isAdmin && <th>Actions</th>}
            </tr>
          </thead>

          <tbody>
            {licenses.map((l) => (
              <tr key={l.licenseKey}>
                <td>{l.licenseKey}</td>
                <td>{l.softwareName}</td>
                <td>{l.validFrom}</td>
                <td>{l.validTo}</td>
                <td>{l.maxUsage}</td>

                {isAdmin && (
                  <td>
                    <button onClick={() => openEditForm(l)}>Edit</button>
                    <button onClick={() => handleDelete(l.licenseKey)}>
                      Delete
                    </button>
                  </td>
                )}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
}
