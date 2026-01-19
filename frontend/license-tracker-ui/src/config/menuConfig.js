// src/config/menuConfig.js
export const MENU_BY_ROLE = {
  ADMIN: [
    { label: "Dashboard", path: "/dashboard" },
    { label: "Devices", path: "/devices" },
    { label: "Licenses", path: "/licenses" },
    { label: "Assignments", path: "/assignments" },
    { label: "Alerts", path: "/alerts" },
    { label: "Reports", path: "/reports" },
    { label: "Lifecycle", path: "/lifecycle" },
    { label: "AI Bot", path: "/ai-bot" },
  ],

  ENGINEER: [
  { label: "Devices", path: "/devices" },
  { label: "Licenses", path: "/licenses" },
  { label: "Assignments", path: "/assignments" },
  { label: "Alerts", path: "/alerts" },
  { label: "Lifecycle", path: "/lifecycle" },
  { label: "AI Bot", path: "/ai-bot" },
  ],

  AUDITOR: [
    { label: "Dashboard", path: "/dashboard" },
    { label: "Alerts", path: "/alerts" },
    { label: "Reports", path: "/reports" },
    { label: "Lifecycle", path: "/lifecycle" },
    { label: "AI Bot", path: "/ai-bot" },
  ],
};
