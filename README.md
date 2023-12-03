# Requirements

**I.** **Xây dựng Web API bằng java, mySQL cho các tính năng sau, (mỗi tính năng cần API Design và Database Design).**

1. Tạo account và đăng nhập: user sẽ đăng kí mới tài khoản bằng email và password. User được tạo ra có role là user, khi đăng nhập sẽ trả về otp, nhập otp để lấy token. OTP thường trả về qua điện thoại, trong bài này opt sẽ trả về luôn khi đúng username và password.

2. Quên mật khẩu: user nhấn nút quên mật khẩu, một đường link sẽ được trả về để tạo mật khẩu mới. Đường link thông thường sẽ gửi qua email, nhưng do không có mail server nên sẽ trả thẳng về bằng API response. Trong link sẽ trả về một token, sử dụng token đó để gọi API tạo mật khẩu mới.

3. Quản lý thông tin cá nhân: user sẽ lưu các thông tin cá nhân của mình như: avatar, tên thật, ngày tháng năm sinh, nghề nghiệp, nơi sống, etc như thông tin của facebook đang có. Lưu ý: có upload ảnh làm avatar.

4. Tính năng đăng bài: user đăng các bài viết lên trang cá nhân bao gồm: nội dung chữ, ảnh hoặc cả 2 đồng thời. (them edit status)

5. Tính năng comment: user có thể comment vào bài viết cá nhân hoặc của user khác.

6. Tính năng like: user có thể ấn like và unlike bài viết cá nhân hoặc của user khác.

7. Tính năng kết bạn: user có thể ấn kết bạn và đồng ý kết bạn với người khác.

8. Tính năng timeline: user vào trang chủ, trang chủ sẽ hiển thị tất cả các bài đăng gần nhất của bạn bè.

9. Tính năng báo cáo: user ấn vào nút tạo file báo cáo, file excel được xuất ra hiển thị các nội dung: số bài đã viết tuần qua, số bạn bè mới, số like và số comment mới tuần qua.

**II.** **Unit Test >80**

Sử dụng Postman hoặc Swagger để test API (trong đó khuyến khích sử dụng Swagger)