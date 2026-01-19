import { useState } from "react";
import Navbar from "../components/Navbar";
import api from "../api/axiosConfig";
import "../styles/ai-bot.css";

export default function AiBot() {
  const role = localStorage.getItem("role");

  const [question, setQuestion] = useState("");
  const [answer, setAnswer] = useState("");
  const [loading, setLoading] = useState(false);

  // ================= ASK AI =================
  const askAi = async () => {
    if (!question.trim()) return;

    setLoading(true);
    setAnswer("");

    try {
      const res = await api.post("/api/ai/query", {
        question,
      });

      setAnswer(res.data.answer);
    } catch (err) {
      console.error(err);
      setAnswer("❌ Unable to fetch AI response");
    } finally {
      setLoading(false);
    }
  };

  // ================= PROJECT-JOURNEY BASED QUESTIONS =================
  const suggestedQuestionsByRole = {
    ADMIN: [
      // Dashboard & Overall Governance
      "Give overall license compliance health",

      // Renewal & Procurement
      "Which licenses need immediate renewal?",

      // Usage Control
      "Are any licenses overused?",

      // Risk Management
      "How many devices are currently non-compliant?",

      // Admin Actions
      "What actions should I take to stay compliant?"
    ],

    ENGINEER: [
      // Daily Operations
      "Are there any devices at risk today?",

      // License Impact on Devices
      "Which devices are affected by expiring licenses?",

      // Assignment Awareness
      "Give me a summary of license assignments",

      // Daily Guidance
      "What actions should I take today?",

      // Software Lifecycle
      "Are any devices running outdated software?"
    ],

    AUDITOR: [
      // Audit Readiness
      "Is the system compliant for audit?",

      // Risk Assessment
      "Show audit risk summary",

      // Expiry Validation
      "How many licenses are expired?",

      // Device Compliance
      "How many devices are non-compliant?",

      // Usage Governance
      "Is license usage within limits?"
    ],
  };

  return (
    <>
      <Navbar />

      <div className="ai-page">
        {/* ================= TITLE ================= */}
        <h2 className="ai-title">
          🤖 AI Compliance Assistant
          <span className="ai-subtitle">
            Smart insights based on your role
          </span>
        </h2>

        <p className="ai-role">
          Logged in as: <strong>{role}</strong>
        </p>

        {/* ================= INPUT ================= */}
        <div className="ai-input-box">
          <textarea
            placeholder="Ask a license compliance question..."
            value={question}
            onChange={(e) => setQuestion(e.target.value)}
          />

          <button onClick={askAi} disabled={loading}>
            {loading ? "Analyzing..." : "Ask AI"}
          </button>
        </div>

        {/* ================= SUGGESTED QUESTIONS ================= */}
        <div className="ai-suggestions">
          <p>Suggested questions based on your role:</p>

          <div className="chip-container">
            {(suggestedQuestionsByRole[role] || []).map((q, i) => (
              <button
                key={i}
                className="chip"
                onClick={() => setQuestion(q)}
                title="Click to ask this question"
              >
                {q}
              </button>
            ))}
          </div>
        </div>

        {/* ================= AI RESPONSE ================= */}
        {answer && (
          <div className="ai-response">
            <pre>{answer}</pre>
          </div>
        )}
      </div>
    </>
  );
}
