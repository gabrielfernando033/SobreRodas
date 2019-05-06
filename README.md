![alt text](https://github.com/gabrielfernando033/SobreRodas/blob/master/Readme/Banner.jpg?raw=true)

# Sobre Rodas
O Sobre Rodas é um aplicativo Open Source que permite os usuários cadastrarem os locais sem acessibilidade no mapa. Com os locais sem acessibilidade cadastrados corretamente, os cadeirantes conseguem traçar rotas mais acessíveis para o seu dia a dia.

Imagine você chegando em um prédio e o elevador esta quebrado e você tem que subir vários andares para chegar ao seu destino. Ou então, você está com pressa e o seu ônibus não parou para pegá-lo pois não teria suporte para você. Imaginou? Esses são apenas alguns exemplos de como a falta de acessibilidade pode atrapalhar nossas vidas.

Muitos cadeirantes passam por problemas de acessibilidade todos os dias, e você pode ajudá-los a mapear esses problemas. Com o Sobre Rodas é possível detalhar os locais sem acessibilidade, com imagens, descrição e categoria, e cadastrá-los no mapa para que os cadeirantes possam ter uma maior transparência da acessibilidade em suas cidades.

## Screenshots
<img width="150" hspace="5" src="https://github.com/gabrielfernando033/SobreRodas/blob/master/Readme/Screenshot1.jpg?raw=true" /><img width="150" hspace="5" src="https://github.com/gabrielfernando033/SobreRodas/blob/master/Readme/Screenshot2.jpg?raw=true" /><img width="150" hspace="5" src="https://github.com/gabrielfernando033/SobreRodas/blob/master/Readme/Screenshot3.jpg?raw=true" /><img width="150" hspace="5" src="https://github.com/gabrielfernando033/SobreRodas/blob/master/Readme/Screenshot4.jpg?raw=true" /><img width="150" hspace="5" src="https://github.com/gabrielfernando033/SobreRodas/blob/master/Readme/Screenshot5.jpg?raw=true" /><img width="150" hspace="5" src="https://github.com/gabrielfernando033/SobreRodas/blob/master/Readme/Screenshot6.jpg?raw=true" /><img width="150" hspace="5" src="https://github.com/gabrielfernando033/SobreRodas/blob/master/Readme/Screenshot7.jpg?raw=true" /><img width="150" hspace="5" src="https://github.com/gabrielfernando033/SobreRodas/blob/master/Readme/Screenshot8.jpg?raw=true" />

## Configurar o projeto
Para rodar o projeto corretamente é necessário possuir uma chave de API do Google Maps para ter acesso ao mapa, e um APP ID criado no Facebook Developers para obter a integração com o login do Facebook.

- Para configurar a chave de API do Google Maps, altere a o valor da chave `google_maps_key`, localizada nos arquivos `app/src/debug/res/values/google_maps_api.xml` e `app/src/release/res/values/google_maps_api.xml`, para o valor da sua chave de API do Google.

- Para configurar o seu APP ID do Facebook, é preciso alterar o valor da string `facebook_app_id1`, localizado no arquivo `app/src/main/res/values/facebook_login_api.xml`, para o valor de seu APP ID. No mesmo arquivo, existe a string `fb_login_protocol_scheme1` onde deverá ser substituido pelo seu App Protocol Scheme.