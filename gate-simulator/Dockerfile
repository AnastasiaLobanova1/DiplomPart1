# базовый образ Node.js LTS
FROM node:lts-alpine

# определение переменных среды
ENV HOME=/app
ENV NODE_ENV=production
ENV NODE_PORT=9999

# установка рабочего каталога
WORKDIR $HOME

# копирование файлов
COPY ./gate-simulator/package.json $HOME/
COPY ./gate-simulator/package-lock.json $HOME/
COPY ./gate-simulator/data.json $HOME/
COPY ./gate-simulator/app.js $HOME/

# установка модулей приложения
RUN npm install && npm cache clean --force

# выставление порта на хосте
EXPOSE $NODE_PORT

# команда запуска приложения
CMD [ "npm", "start" ]