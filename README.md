# ویلی ارز — اپ Native واقعی (Jetpack Compose)

## نسخه 2.0.0-native

این یک اپلیکیشن **Native اندروید** است (نه WebView).

### صفحات پیاده‌سازی‌شده
- لیست سیگنال‌ها با فیلتر (فیوچرز / اسپات / فارکس / رایگان)
- جزئیات سیگنال
- اعلان‌های همگانی
- ورود با ایمیل/موبایل
- پروفایل + وضعیت اشتراک
- درخواست پرمیشن نوتیفیکیشن

### اتصال به API
Base URL: `https://www.ville-trade.ir/`

### بیلد
1. پروژه را در Android Studio (Hedgehog یا جدیدتر) باز کنید
2. Gradle Sync
3. Build → Build APK(s)

یا:
```bash
./gradlew assembleDebug
```

### پکیج
`ir.villetrade.app` — versionCode 40

### مرحله بعد (در صورت نیاز)
- ثبت‌نام کامل
- پرداخت مایکت Native
- FCM برای پوش واقعی
- نتایج معاملات
- تیکت پشتیبانی
