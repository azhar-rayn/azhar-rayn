import * as dotenv from "dotenv";
dotenv.config({ path: __dirname+'/.env' });

const USER = "testuser"//process.env.USER
const PASSWROD = "testpassword"//process.env.PASSWORD
const HOST = "localhost"//process.env.PASSWORD
const DB = "dost"// process.env.DB
const PORT = 5000
const APP_SECRET = "SECRET"//process.env.APP_SECRET

export { USER, PASSWROD, HOST, DB, PORT, APP_SECRET }

