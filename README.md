# Introduction to Software Engineering

- Group comprises of 4 members: **MinhTC** (Team leader), **LamNT**, **KhanhTV**, and **PhuocLX**
- This respository is created to work with the project in the subject "Introduction to Software Engineering".

# Project Quản lý Tổ dân phố

## Mục lục

1. [Set up môi trường](#first)
2. [Set up cơ sở dữ liệu](#second)
3. [Quy trình làm việc](#third)

<span id="first" style="font-size: 16px;"><b>1. Set up môi trường</b></span><br/>
Cài đặt các công cụ cần thiết:

  - <a target="_blank" rel="noopener noreferrer" href="https://www.jetbrains.com/idea/download/download-thanks.html?platform=windows&code=IIC">IntelliJ</a>
  - <a href="https://www.apachefriends.org/download.html" target="_blank" rel="noopener noreferrer">XAMPP</a>
  - <a id="file" href="https://drive.google.com/file/d/1Na2t1oi6tL9ae6F_Zs-Jucmq1AZUhC64/view?usp=sharing" target="_blank" rel="noopener noreferrer">File quan_ly_nhan_khau.sql</a>

<span id="second"><b>2. Set up cơ sở dữ liệu</b></span><br/> 

- Mở XAMPP, chọn start Apache và MySQL 

<p align="center" >
  <img width="50%" src="https://user-images.githubusercontent.com/91966779/215268786-f508254f-52c3-426a-9924-48eebc3dc58c.png"/>
</p>


- Chọn Admin ở mục MySQL

<p align="center" >
  <img width="50%" src="https://user-images.githubusercontent.com/91966779/215268991-6e93abaf-4bf9-47a2-ae3d-ddfd9a8958b0.png"/>
</p>


- Chon new để tạo cơ sở dữ liệu mới 

<p align="center" >
  <img width="50%" src="https://user-images.githubusercontent.com/91966779/215269091-c462168e-04f5-4691-846a-cb59981d97ce.png"/>
</p>



- Nhập tên cơ sở dữ liệu: "quan_ly_nhan_khau", chọn character set: utf8_mb4_vietnamese_ci, sau đó chọn create

<p align="center" >
  <img width="50%" src="https://user-images.githubusercontent.com/91966779/215269300-1ae68c47-1968-4ae4-8ead-db52acf0416f.png"/>
</p>


- Chọn cơ sở dữ liệu vừa tạo "quan_ly_nhan_khau"

<p align="center" >
  <img width="70%" src="https://user-images.githubusercontent.com/91966779/215269365-ee720735-c057-4389-a535-1e4cc4ea051d.png"/>
</p>


- Chọn import 

<p align="center" >
  <img width="70%" src="https://user-images.githubusercontent.com/91966779/215269425-09c2b2a9-72f2-40d8-aa72-0ab8d78663af.png"/>
</p>


- Chọn browse và chọn file ["quan_ly_nhan_khau.sql"](#file) đã download ở trên
- Kéo xuống chọn import 

<p align="center" >
  <img width="70%" src="https://user-images.githubusercontent.com/91966779/215270114-37a293a6-c523-4022-afbe-c15b9915e63f.png"/>
</p>


<span id="third"><b>3. Quy trình làm việc</b></span><br/> 

- Clone repo về 

```
git clone git@github.com:TVKain/QuanLyNhanKhau.git 
```

- Import vào Intellij (Chọn File => Open => Chọn Folder vừa clone về) 
- Mở terminal tại project và tạo branch mới 

```
git branch [tên branch]
```

- Code phần của mình 
- Xong rồi thì 

```
git add . 
git commit -m "Nội dung commit"
```

- Push code lên repo 

```
git push -u origin [tên branch hiện tại]
```

- Pull code từ main về trước khi tạo pull request (Để không bị merge conflict)

```
git pull origin main
```

- Tạo pull request 