# License & Asset Compliance Management Platform

A full-stack enterprise application for managing software licenses, IT assets, assignments, vendors, compliance, audit activity, expiry alerts, reporting, and AI-assisted compliance queries.

The platform provides a centralized view of an organization's software licensing and device inventory, helping teams track license utilization, identify upcoming expirations, maintain compliance, and generate operational reports.

---

## Overview

Managing software licenses and IT assets across an organization can become difficult when information is distributed across spreadsheets, systems, and manual processes.

This application provides a centralized platform to:

* Manage software licenses
* Track devices and IT assets
* Manage software vendors
* Assign licenses to devices/users
* Monitor license and device expiry
* Track audit activities
* Generate compliance reports
* Provide role-based access
* Query compliance information using an AI-assisted interface

The system follows a layered backend architecture using **Spring Boot, Spring Data JPA, and MySQL**, with a **React + Vite** frontend.

---

## Key Features

### License Management

* Create and manage software licenses
* Track license details and validity periods
* Associate licenses with vendors
* Monitor license status and expiry
* Support license lifecycle management

### Device Management

* Maintain IT device records
* Track device information and lifecycle
* Associate devices with software licenses
* Monitor device-related expiry information

### License Assignment

* Assign licenses to devices/users
* Track existing assignments
* Validate assignment operations
* Prevent invalid or duplicate assignments
* Enforce assignment-related business rules

### Vendor Management

* Maintain vendor information
* Associate vendors with licenses
* Manage vendor-related information used throughout the platform

### Compliance & Alerts

* Identify licenses approaching expiry
* Identify expired licenses
* Track device expiry information
* Provide dedicated alert APIs and UI
* Support compliance monitoring workflows

### Audit Logging

The application maintains audit information for important application activities.

Audit functionality provides:

* Audit log creation and retrieval
* Historical activity tracking
* Audit-oriented APIs
* Support for compliance investigations

### Reporting

The platform provides reporting capabilities for compliance and operational information.

Reports can be generated and exported for further analysis and sharing.

### AI-Assisted Compliance Queries

The application includes an AI module integrated with **Google Gemini**.

Users can su
