# Tax Filing Management App 💼📱

📌 **Project Overview**
The **Tax Filing Management App** is an Android application designed to streamline tax filing processes. It provides a user-friendly platform where customers can register, view, and update their details, while admins can manage customer data, update statuses, and track progress efficiently. This app demonstrates robust data handling, interactive UI, and real-world application in tax management.

---

🌍 **Real-World Applications**
The app can be utilized by:
- **Tax Consultants**: To manage and track client information and progress.
- **Filing Firms**: To onboard and process customer tax filings.
- **Educational Purposes**: A practical demonstration of Android development with RoomDB and Geolocation integration.

---

🛠️ **Key Features**

### Registration Screen
- Allows customers to register with personal details such as name, address, and contact information.
- Geocodes the entered address into latitude and longitude for location tracking.

### Login Screen
- Provides login access for both admins and customers with secure authentication.
- Switch option to toggle between admin and customer login modes.

### Admin Home Screen
- Lists all registered customers with details like name, phone, city, and process status.
- Features a color-coded RecyclerView to indicate the status of each customer:
  - **AWAITED**: Yellow tone
  - **FAILEDTOREACH**: Light Red tone
  - **ONBOARDED**: Light Green tone
  - **INPROCESS**: Mid Green tone
  - **COMPLETED**: Dark Green tone
  - **DENIED**: Red tone
- Admins can:
  - Update customer statuses.
  - Delete customer entries via swipe-to-delete with confirmation.

### Customer Home Screen
- Displays the customer’s details and process status.
- Allows customers to edit their personal information, except email.
- Status is view-only, color-coded based on admin updates.

### Customer Detail Screen
- Displays all customer details, including location on an interactive map.
- Allows admins to modify process statuses using a dropdown spinner.
- Real-time updates in the database reflect changes in all views.

### Resources and UI Highlights
- **Custom Layouts**:
  - `activity_admin_home.xml`: Admin dashboard with customer list.
  - `activity_customer_home.xml`: Editable customer profile view.
  - `activity_customer_detail.xml`: Detailed customer information with map view.
  - `spinner_item.xml` and `spinner_dropdown_item.xml`: Dropdown design for selecting statuses.
- **Icons**:
  - `ic_logout_icon.xml`: Logout button design.
  - `ic_launcher_foreground.xml` and `ic_launcher_background.xml`: App launcher assets.
- **RecyclerView Item Layout**:
  - `customer_item.xml`: Represents individual customer entries with a structured format.

---

💻 **Technology Stack**
- **Programming Language**: Java
- **Development Environment**: Android Studio
- **Libraries and Tools**:
  - RoomDB: For local database management.
  - Google Maps API: For geolocation and map integration.
  - Glide: For image handling.
  - Material Design: For a modern and responsive UI.

---

📖 **How to Run the Application**

1️⃣ **Clone the Repository**:
   ```bash
   git clone <repository_url>
   ```

2️⃣ **Open the Project**:
   - Launch Android Studio and select **Open an Existing Project**.
   - Navigate to the cloned folder and open it.

3️⃣ **Sync Dependencies**:
   - Gradle will prompt you to sync dependencies. Click **Sync Now** to ensure the project builds correctly.

4️⃣ **Run the Application**:
   - Use an emulator or connect your Android device to test the app.
   - Click the **Run** button in Android Studio.

5️⃣ **Permissions**:
   - The app requires internet and location access. Ensure the necessary permissions are granted.

---

📷 **Screenshots**

![Screenshot (109)](https://github.com/user-attachments/assets/66615009-08fd-446d-9778-3b85a9ff859d)

![Screenshot (110)](https://github.com/user-attachments/assets/3c4eca93-86bb-45c3-8d97-c3b98754bd99)

![Screenshot (111)](https://github.com/user-attachments/assets/6eab73a9-b24e-4f7d-9fc7-7752b3183fce)

![Screenshot (112)](https://github.com/user-attachments/assets/5b760796-70b9-4cf4-8ebe-09a9a9ef4da8)

![Screenshot (113)](https://github.com/user-attachments/assets/e3e79bb1-59f1-443c-98b2-c613abde14ae)

![Screenshot (114)](https://github.com/user-attachments/assets/36625eec-4680-49b7-9721-f56a7466df46)

![Screenshot (115)](https://github.com/user-attachments/assets/71f8ee4d-514c-4a72-a936-df2b1c13f996)

![Screenshot (116)](https://github.com/user-attachments/assets/73ee5d34-72e3-406a-aa53-2320da6f9fa2)

![Screenshot (117)](https://github.com/user-attachments/assets/5365bb24-fbb5-4228-a2ce-190071b895f5)

![Screenshot (118)](https://github.com/user-attachments/assets/bb6441d8-db9a-4146-99e3-338c45536970)

![Screenshot (120)](https://github.com/user-attachments/assets/7a7c79b7-c0ea-4125-9c93-5d1423c46d76)

---

🚀 **Developed by Adnan Mohammed**
