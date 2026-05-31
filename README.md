## アプリ名
- ラーメン店舗検索アプリ

## コンセプト
- 近所のラーメン屋をすぐ見つけられる。あまり多くない辛いラーメン屋さんを特集するのも目的！

## 開発環境
- Android Studio Panda3

## 開発言語
- Kotlin2.2.10

## 動作対象端末・OS
- Android9.0~

## アプリケーション機能
### 機能一覧
- 店舗検索:ホットペッパーグルメサーチAPIを使用して、現在地周辺のラーメン屋を検索する。
- 店舗情報取得:ホットペッパーグルメサーチAPIを使用して、ラーメン屋の詳細情報を取得する。
- 絞り込み:現在地から半径300m,500m,1km,2km,3kmの店舗を検索できる。

### 画面一覧
- ホーム画面:おすすめ店舗、現在地に近いラーメン屋を表示
- 激辛おすすめ一覧:わたしの行ったことがある激辛ラーメンのあるおすすめ店舗の一覧を表示、店舗情報、辛さレベルを右上に表記。

![sutreru_fav](https://cdn.discordapp.com/attachments/1148075495334883369/1510720964407590982/image.png?ex=6a1dd809&is=6a1c8689&hm=17f8d91198e983e18661eecd3f9abb8a5d6fae31ae62399c4d66aaf557768c41&)![sutreru_fav](https://cdn.discordapp.com/attachments/1148075495334883369/1510720719196127253/image.png?ex=6a1dd7cf&is=6a1c864f&hm=8bb0875856a3912b04b99452d7779b0cf831a569b5ba68a49967f3b6ee707f2a&)
- 検索画面:条件を指定してラーメン屋を検索する
- 一覧画面:検索結果のラーメン屋を一覧表示する

![sutreru_fav](https://cdn.discordapp.com/attachments/1148075495334883369/1510721335549231174/image.png?ex=6a1dd862&is=6a1c86e2&hm=ea4c130e02ade5a08bb91842627a3ef2dc5d6dda3c66c948942268f5005d54e3&)

- 詳細画面:APIから取得した店舗の詳細情報(店舗名/住所/アクセス/外部URL)
- 外部サイトへの遷移:API取得ができなかった店舗や、ボトムシートを採用しているのでより詳細な情報はホットペッパーグルメへの外部遷移として対応
![sutreru_fav](https://cdn.discordapp.com/attachments/1148075495334883369/1510721599052058774/image.png?ex=6a1dd8a1&is=6a1c8721&hm=e44be84c3703f3fb8675d03442d2ff3859579189c3effae8a0f5f65fb8bd3a5c&)


## 使用API,SDK,ライブラリ
- ホットペッパーグルメサーチAPI
### 使用ライブラリ
- play-services-location
- Ktor (2.3.11)
- Navigation Compose (2.9.8)
- Coil (2.6.0)
- Kotlinx Serialization (1.9.0)
- Material Icons Extended
- Activity Compose (1.3.0)
- UI Tooling (1.0.0)
- Material (1.0.1)

## APIkeyの取得方法
- ホットペッパーAPI https://webservice.recruit.co.jp/register に新規登録
- HotPepperClient.ktのapikey=""に取得したAPIを貼ってください。

## その他(現環境におけるバグ・一時的な対処方法)
- キーワード検索をせず絞り込み機能の"すべて"を選択すると、現在地関係なく全国検索になってしまう仕様になってしまっています。すべてを選択する場合は必ずキーワード検索をお願いします。
- 初めての起動でビルドに失敗した場合、お手数をおかけしますがターミナルから直接ビルドをしていただければと思います。
- 
