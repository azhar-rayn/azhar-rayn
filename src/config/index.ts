import * as dotenv from "dotenv";
dotenv.config({ path: __dirname+'/.env' });

const USER = process.env.SPRING_DATASOURCE_USERNAME
const PASSWROD =process.env.SPRING_DATASOURCE_USERNAME
const HOST = process.env.SPRING_DATASOURCE_URL
const DB = "dost"
const DB_PORT = 5432
const PORT = 5000
const APP_SECRET = "SECRET"//process.env.APP_SECRET

export { USER, PASSWROD, HOST, DB, PORT, APP_SECRET, DB_PORT }

