Note:
The latest version is under development and we will try to make it close to the real ytdlnis. Also 
Keep knowing that the current version is outdated and full of bugs and we will work as much as we can to make it the best after the Android version. 



# YTDLnipc Desktop

A powerful desktop video/audio downloader powered by yt-dlp, ported from the popular [YTDLnis Android app](https://github.com/deniscerri/ytdlnis).

![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue)
![Compose](https://img.shields.io/badge/Compose%20Desktop-1.7.3-green)
![License](https://img.shields.io/badge/License-MIT-yellow)

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🎵 **Audio Downloads** | Download audio in MP3, M4A, OPUS, FLAC, and more |
| 🎬 **Video Downloads** | Download videos in MP4, MKV, WebM with quality selection |
| 📋 **Queue Management** | Manage multiple downloads with pause/resume support |
| 📜 **Download History** | Track all your downloads with re-download capability |
| 🔧 **Command Templates** | Create custom yt-dlp commands for advanced users |
| 🍪 **Cookie Support** | Import cookies for authenticated downloads |
| 👁️ **Observe Sources** | Monitor channels/playlists for new content |
| 💻 **Terminal Mode** | Execute raw yt-dlp commands directly |
| 🎨 **Material 3 UI** | Modern, beautiful interface with dark mode |

---

## 🚀 Getting Started

### Prerequisites

1. **Java 21** - [Download from Oracle](https://www.oracle.com/java/technologies/downloads/#java21) or [Adoptium](https://adoptium.net/)
2. **yt-dlp** - Install via:
   ```bash
   # Windows (winget)
   winget install yt-dlp
   
   # Windows (pip)
   pip install yt-dlp
   
   # Linux
   sudo apt install yt-dlp  # or use pip
   
   # macOS
   brew install yt-dlp
   ```
3. **FFmpeg** (recommended) - For audio conversion and video merging
   ```bash
   # Windows (winget)
   winget install FFmpeg
   
   # Linux
   sudo apt install ffmpeg
   
   # macOS
   brew install ffmpeg
   ```

### Running the App

```bash
# Clone the repository
git clone https://github.com/wizardcargo8080/ytdlnipc-desktop.git
cd ytdlnipc-desktop

# Run the application
./gradlew desktopApp:run     # Linux/macOS
.\gradlew.bat desktopApp:run # Windows
```

### Building a Distribution

```bash
# Create a native distribution package
./gradlew desktopApp:packageDistributionForCurrentOS
```

---

## 🤝 Contributing

We welcome contributions from the community! Here's how you can help:

### Areas Needing Help

- 🐛 **Bug Fixes** - Check [open issues](../../issues) and help squash bugs
- 🔄 **yt-dlp Updates** - Keep command building in sync with yt-dlp changes
- 🎨 **UI Improvements** - Enhance the user interface and UX
- 📱 **Feature Parity** - Port remaining YTDLnis Android features
- 📖 **Documentation** - Improve docs and add usage guides
- 🌐 **Translations** - Help translate the app to other languages

### How to Contribute

# A - issues tab
1. **navigate to issues tab** go to the issues tab in this repo. 
2. **open new issue** click on the green button with the text of "new issue". 
3. **Implementation** put your github's username in the first line in the description and the title write your request in this In our case contribution request. 
4. **Submit** submit the issue in the range of 1 day you will be in. 
   
# B - Fork
1. **Fork** the repository
2. **Create** a feature branch: `git checkout -b feature/amazing-feature`
3. **Commit** your changes: `git commit -m 'Add amazing feature'`
4. **Push** to the branch: `git push origin feature/amazing-feature`
5. **Open** a Pull Request

### Code Style

- Follow Kotlin coding conventions
- Use meaningful commit messages
- Add tests for new functionality
- Update documentation as needed

---

## 🔄 Staying Up to Date

### With yt-dlp

yt-dlp is actively developed with frequent updates. To ensure compatibility:

1. **Update yt-dlp regularly**:
   ```bash
   yt-dlp -U
   # or
   pip install -U yt-dlp
   ```

2. **Check for breaking changes** in [yt-dlp releases](https://github.com/yt-dlp/yt-dlp/releases)

3. **Report issues** if downloads fail after yt-dlp updates

### With YTDLnis Android

This project aims to maintain parity with the original [YTDLnis Android app](https://github.com/deniscerri/ytdlnis):

- 📢 Watch the main repo for new features
- 🔀 Help port new features to desktop
- 📝 Reference Android implementation for consistency

---

## 📁 Project Structure

```
ytdlnis-desktop/
├── shared/                    # Shared KMP module
│   └── src/commonMain/
│       ├── kotlin/            # Data models, utilities
│       └── sqldelight/        # Database schema
├── desktopApp/                # Desktop application
│   └── src/jvmMain/kotlin/
│       ├── platform/          # yt-dlp integration
│       └── ui/                # Compose Desktop UI
└── gradle/                    # Gradle wrapper
```

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- [YTDLnis](https://github.com/deniscerri/ytdlnis) - The original Android app by Denis Cerri
- [yt-dlp](https://github.com/yt-dlp/yt-dlp) - The powerful download engine
- [JetBrains Compose](https://www.jetbrains.com/lp/compose-multiplatform/) - The UI framework

---
# Contact
Feel free to put your ideas and discuss in [our discord server](https://discord.gg/B8AR2ztHx) 


---

<p align="center">
  <b>⭐ Star this repo if you find it useful!</b><br>
  <i>Made with ❤️ by @wizardcargo8080</i>
</p>
