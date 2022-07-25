import "reflect-metadata"
import {DB,HOST,PASSWROD,USER, DB_PORT} from './config'
import { DataSource } from "typeorm"
import {
    Address,
    Candidate_Experience,
    Category,
    Certificate,
    Classification,
    Company,
    Degree,
    Education,
    Education_Field,
    Industry,
    Instituation,
    Job,
    Job_Application,
    Resume,
    Saved_Job,
    Skill,
    User,
    UserSkill,
    User_Profile
} from "./entity"


export const AppDataSource = new DataSource({
    type: "postgres",
    host: HOST,
    port: 5432,
    username: USER,
    password: PASSWROD,
    database: "dost",
    synchronize: true,
    logging: false,
    entities: [
        Address,
        Candidate_Experience,
        Category,
        Certificate,
        Classification,
        Company,
        Degree,
        Education,
        Education_Field,
        Industry,
        Instituation,
        Job,
        Job_Application,
        Resume,
        Saved_Job,
        UserSkill,
        Skill,
        User,
        User_Profile],
    migrations: [],
    subscribers: [],
})
