# SmartSEE 👁️📱

## Erişilebilirlik Odaklı Android Nesne Tanıma Uygulaması (YOLOv8 + TensorFlow Lite)
## Accessibility-Oriented Android Object Detection Application (YOLOv8 + TensorFlow Lite)

## 🇹🇷 

SmartSEE, görme engelli veya görme zorluğu yaşayan bireylere yardımcı olma fikriyle başlatılmış, Android (Java) platformunda geliştirilmiş bir nesne tanıma uygulamasıdır.
Uygulama, özel eğitilmiş **YOLOv8 TensorFlow Lite modeli** kullanarak klavye ve mouse nesnelerini algılar ve sonucu **Türkçe Text-to-Speech (TTS)** ile sesli olarak bildirir.
Bu proje, mobil cihazlarda yapay zeka modellerinin uygulanabilirliğini ve erişilebilirlik (accessibility) odaklı çözümleri deneysel olarak geliştirmeyi amaçlamaktadır.

### 🎯 Projenin Amacı

- Görme engelli bireyler için yardımcı bir mobil prototip geliştirmek
- YOLOv8 modelini Android üzerinde TensorFlow Lite ile çalıştırmak
- Gerçek zamanlı nesne tanıma ve sesli geri bildirim sağlamak
- Mobil yapay zeka entegrasyonu üzerine deneyim kazanmak

### 🧠 Model Bilgileri

- Model: YOLOv8 (TensorFlow Lite)
- Giriş Boyutu: 640x640
- Çıkış: `[1, 8, 8400]`
- Sınıflar:
  - black_mouse
  - white_mouse
  - black_keyboard
  - white_keyboard

### ⚙️ Kullanılan Teknolojiler

- Android Studio
- Java
- TensorFlow Lite
- YOLOv8 (Özel Eğitilmiş Model)
- Android TextToSpeech (Türkçe)
- ConstraintLayout

### 🚀 Uygulama Özellikleri

- Kamera ile gerçek zamanlı görsel alma
- Galeriden görsel seçme
- Algılanan etiketi ekranda gösterme
- Türkçe sesli okuma (TTS geri bildirim)
- Özel eğitilmiş model entegrasyonu

### ⚠️ Proje Durumu

Bu proje aktif olarak geliştirilmektedir.

Planlanan iyileştirmeler:

- TTS kararlılığını artırma
- Model doğruluğunu artırma
- Performans optimizasyonu
- Arayüz geliştirmeleri
- Yanlış sınıflandırma oranını azaltma

### 📦 APK

APK dosyası **GitHub Releases** bölümünde paylaşılmıştır.

---

## 🇬🇧 

SmartSEE is an Android (Java) object detection application initially designed with an accessibility perspective to assist visually impaired or visually challenged individuals.
The app uses a custom-trained **YOLOv8 TensorFlow Lite model** to detect keyboard and mouse objects and provides **Turkish Text-to-Speech (TTS)** audio feedback.
This project explores the practical deployment of AI models on mobile devices with a focus on accessibility-oriented solutions.

### 🎯 Project Purpose

- Develop an assistive mobile prototype for visually impaired users
- Deploy YOLOv8 on Android using TensorFlow Lite
- Provide real-time object detection with audio feedback
- Gain hands-on experience in mobile AI integration

### 🧠 Model Information

- Model: YOLOv8 (TensorFlow Lite)
- Input Size: 640x640
- Output: `[1, 8, 8400]`
- Classes:
  - black_mouse
  - white_mouse
  - black_keyboard
  - white_keyboard

### 🛠 Tech Stack

- Android Studio
- Java
- TensorFlow Lite
- YOLOv8 (Custom Trained Model)
- Android TextToSpeech (Turkish)

### 🚀 Features

- Real-time camera capture
- Image selection from gallery
- Display detected label on screen
- Turkish voice feedback (TTS)
- Custom-trained model integration

### ⚠️ Status

Work in progress.

Planned improvements:

- Improve TTS stability
- Increase model accuracy
- Performance optimization
- UI improvements
- Reduce misclassification rate

### 📦 APK

The APK file is shared under the **GitHub Releases** section.


## 👤 Yigit Ali Ayyildiz
