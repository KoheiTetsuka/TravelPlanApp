# TravelPlanApp
## 概要
簡単に旅行の計画が立てられるシンプルなAndroidアプリです。

## DeployGate

## 画面一覧
|旅行プラン一覧画面|旅行プラン作成画面|旅行プラン編集画面|
|---|---|---|
|<img src="https://github.com/KoheiTetsuka/TravelPlanApp/assets/58130056/f6783e22-fd60-4683-bb1f-7d426a3db9ca" width="100%">|<img src="https://github.com/KoheiTetsuka/TravelPlanApp/assets/58130056/c828e704-2cd6-4bdd-818d-5a93db070e24" width="100%">|<img src="https://github.com/KoheiTetsuka/TravelPlanApp/assets/58130056/0bd8e14a-692e-4970-9ca6-7f6f46ec6f06" width="100%">|

|スケジュール一覧画面|スケジュール作成画面|スケジュール編集画面|
|---|---|---|
|<img src="https://github.com/KoheiTetsuka/TravelPlanApp/assets/58130056/5cbd51d3-6708-4c0f-9de0-18b1b292111f" width="100%">|<img src="https://github.com/KoheiTetsuka/TravelPlanApp/assets/58130056/8d876011-2909-46dc-a461-3b59aced795a" width="100%">|<img src="https://github.com/KoheiTetsuka/TravelPlanApp/assets/58130056/c497fe6b-e1e8-43e4-a8cd-995672457793" width="100%">|

|持ち物一覧画面|持ち物リスト作成画面|持ち物リスト編集画面|
|---|---|---|
|<img src="https://github.com/KoheiTetsuka/TravelPlanApp/assets/58130056/c4e32cbe-8310-4a28-90f3-09e2be426545" width="100%">|<img src="https://github.com/KoheiTetsuka/TravelPlanApp/assets/58130056/57524d33-4ec7-4418-a74c-2b60204c407c" width="100%">|<img src="https://github.com/KoheiTetsuka/TravelPlanApp/assets/58130056/18d650b3-22c5-4427-9e57-5cfa25f2b815" width="100%">|

## 機能説明
+ 一覧画面
  + 登録した内容の一覧を閲覧、削除をすることがで
  + 持ち物一覧画面ではチェックボックスにて準備した持ち物を削除することができる
+ 登録画面
  + 旅行プラン、スケジュール、持ち物の登録をすることができる
+ 編集画面
  + 旅行プラン、スケジュール、持ち物の編集をすることができる

## 使用技術
+ Jetpack Compose
+ Kotlin Coroutine
+ Room

## 工夫点
+ 登録画面、編集画面のエラーハンドリング
  + 必須項目が未入力の場合のエラー処理や、終了日が開始日より前の日付だった場合のエラー処理などを実装しました。

|必須未入力エラー|終了日>開始日のエラー|
|---|---|
|<img src="https://github.com/KoheiTetsuka/TravelPlanApp/assets/58130056/d1be2e03-3c7e-49bf-8486-fda61b54a385" width="100%">|<img src="https://github.com/KoheiTetsuka/TravelPlanApp/assets/58130056/1236d8ab-2e62-4260-a510-ce6adc402ab5" width="100%">|

## 今後の展望
+ Google Maps API の導入
  + 本アプリ内からGoogle Mapを開き、目的地までのルート案内を表示できるようにします。

+ 登録画面、編集画面の共通化
  + ほぼ同一の画面であるにも関わらず、別画面として作成をしている為、共通化を行います。
