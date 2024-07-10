# NLW Journey - Mobile

## Tecnologias

- Typescript / Javascript
- Expo / React Native
- NativeWind / Tailwind CSS

### Bibliotecas adicionais

- expo-font
- expo-google-fonts/inter
- clsx
- react-native-svg
- lucide-react-native
- react-native-calendars
- expo-blur
- axios
- zod
- react-native-async-storage/async-storage

## Galeria de imagens

<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-01.png" alt="NLW Journey Mobile 01" height="400"/>
<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-02.png" alt="NLW Journey Mobile 02" height="400"/>
<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-03.png" alt="NLW Journey Mobile 03" height="400"/>
<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-04.png" alt="NLW Journey Mobile 04" height="400"/>

<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-05.png" alt="NLW Journey Mobile 05" height="400"/>
<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-06.png" alt="NLW Journey Mobile 06" height="400"/>
<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-07.png" alt="NLW Journey Mobile 07" height="400"/>
<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-08.png" alt="NLW Journey Mobile 08" height="400"/>

<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-09.png" alt="NLW Journey Mobile 09" height="400"/>
<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-10.png" alt="NLW Journey Mobile 10" height="400"/>
<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-11.png" alt="NLW Journey Mobile 11" height="400"/>
<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-12.png" alt="NLW Journey Mobile 12" height="400"/>

<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-13.png" alt="NLW Journey Mobile 13" height="400"/>
<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-14.png" alt="NLW Journey Mobile 14" height="400"/>
<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-15.png" alt="NLW Journey Mobile 15" height="400"/>
<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-16.png" alt="NLW Journey Mobile 16" height="400"/>

<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-mobile-17.png" alt="NLW Journey Mobile 17" height="400"/>

## Rodar

### Requisitos

- Node.js 20

### Comandos

#### Rodar o servidor

- entrar na pasta nodejs
- renomear .env.example para .env
- comando: npm install
- comando: npm run dev

#### Rodar o aplicativo

- entrar na pasta mobile
- comando: npm install
- comando: npx expo start
- no dispositivo físico: instalar o app Expo Go
- no dispositivo físico: estando na mesma rede do PC
- no dispositivo físico: escanear o QRCode que aparece

## Teste do app link

A. Por terminal

- npx expo prebuild
- npx expo run:android
- npx uri-scheme open "planner://trip/46d156c0-c966-4b8f-89ac-a41b46ac7d37?participant=f057f9dd-d775-4b22-8b60-bc97a69df1b8" --android

B. Por simulador de e-mail

- obs: a viagem deve estar confirmada
- abrir no vscode com a extensão rest client instalada: api.http
- clicar: send request
- no log do servidor nodejs terá um link, copie o link
- no navegador do dispositivo android, cole o link
- vai abrir uma página de simulação de e-mail: clicar em Confirmar viagem no corpo do e-mail
