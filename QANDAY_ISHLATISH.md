# Vault — ishga tushirish qo'llanmasi

## 1. APK yasash (Android Studio orqali, bepul)
1. Android Studio'ni o'rnating: https://developer.android.com/studio
2. "Open" → shu `VaultApp` papkasini tanlang.
3. Gradle sinxronlanishini kuting (birinchi marta 2-5 daqiqa, internet kerak).
4. Yuqoridagi menyudan: **Build → Build Bundle(s) / APK(s) → Build APK(s)**.
5. Tugagach "locate" tugmasini bosing — APK shu yerda:
   `app/build/outputs/apk/debug/app-debug.apk`

## 2. Telefonga o'rnatish
1. APK faylni telefonga ko'chiring (kabel, Telegram "Saved Messages" va h.k.).
2. Telefonda fayl manejerda APK'ni oching → "Noma'lum manbalardan o'rnatish"ga ruxsat bering → O'rnating.

## 3. Sozlash
1. Ilovani oching → 4 xonali **PIN** o'rnating (birinchi marta).
2. Telefon so'raydi: "Qaysi ilova bilan Home tugmasini bosasiz?" — **Vault**'ni tanlang va
   **"Har doim" / "Always"** ni bosing. (Agar so'ramasa: Sozlamalar → Ilovalar →
   Standart ilovalar → Bosh ekran ilovasi → Vault.)
3. Shundan keyin Home tugmasi bosilganda — avval PIN so'raydi.

## 4. Ilova yashirish
1. PIN kiriting → oddiy bosh ekran chiqadi (yashirilmagan ilovalar shu yerda).
2. Pastki o'ng burchakdagi 🔒 tugmani bosing → Vault ochiladi.
3. Pastdagi **+** tugmani bosing → telefondagi barcha ilovalar ro'yxati chiqadi.
4. Kerakli ilovani bosing — u darhol Vault ichiga ko'chadi va oddiy bosh ekrandan yo'qoladi.
5. Vault ichida bosilsa — ilova ochiladi. Olib tashlash uchun ustida bosib turing.

## Muhim — nima ishlaydi, nima ishlamaydi
- ✅ Tanlangan ilova **shu Vault launcher**ning bosh ekrani va ilovalar ro'yxatidan yo'qoladi.
- ✅ Home tugmasi endi to'g'ridan-to'g'ri PIN ekraniga olib boradi.
- ⚠️ Ilova baribir **Sozlamalar → Ilovalar** ro'yxatida va telefon agar **boshqa launcher**
  o'rnatilgan bo'lsa o'sha yerda ko'rinishi mumkin — Android buni oddiy ilovaga butunlay
  man qilish huquqini bermaydi (faqat root bilan bo'ladi).
- ⚠️ Recents (so'nggi ilovalar) tugmasi orqali ham ilova ko'rinishi mumkin, agar u fonda
  ishlab turgan bo'lsa.
