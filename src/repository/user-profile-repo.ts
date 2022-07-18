import { Certificate, Degree, Education, User_Profile } from '../entity';
import { Repository } from 'typeorm';
import { AppDataSource } from '../data-source'
import { Candidate_Experience, Category, Skill, User, UserSkill } from '../entity'
import { APIError, STATUS_CODES } from '../utils/app-errors';


export class UserProfileRepository {

  experience: Repository<Candidate_Experience>
  skill: Repository<UserSkill>
  certificate: Repository<Certificate>
  education: Repository<Education>
  user: Repository<User>
  profile: Repository<User_Profile>

  constructor() {
    this.experience = AppDataSource.getRepository(Candidate_Experience);
    this.skill = AppDataSource.getRepository(UserSkill);
    this.certificate = AppDataSource.getRepository(Certificate);
    this.education = AppDataSource.getRepository(Education);
    this.user = AppDataSource.getRepository(User);
    this.profile = AppDataSource.getRepository(User_Profile);
  }

  //#######################################################################
  async AddProfile(
    userID: string,
    CNIC: number,
    employed: boolean,
    firstName: string,
    lastName: string,
    dob: Date,
    experience: number,
    gender: string,
    addressID: string,
    classificationID: string): Promise<User_Profile> {


    try {
      return await this.profile.save({
        CNIC,
        dob,
        addressID,
        classificationID,
        employed,
        experience,
        firstName,
        gender,
        lastName,
        userID
      })
    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }

  //#######################################################################
  async GetProfile(userID: string): Promise<User_Profile | null> {

    try {

      return await this.profile.findOne({ where: { userID } })

    } catch (error) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async AddExperience(userID: string, title: string, categoryID: string, companyName: string, city: string, experience: number): Promise<string> {

    try {

      const result = await this.experience.save({ userID, city, companyName, experience, title, categoryID });

      return result.ID

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


  //#######################################################################
  async AddSkill(userID: string, skillID: string, level: string): Promise<string> {

    try {


      const result = await this.skill.save({ userID, skillID, level });

      return result.ID

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


  //#######################################################################
  async AddCertificate(userID: string, topic: string, type: string, instituation: string, date: Date): Promise<string> {

    try {

      const result = await this.certificate.save({ userID, topic, type, instituation, date });

      return result.ID

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async AddEducation(userID: string, degreeID: string, fieldID: string, instituationID: string, year_of_completion: number): Promise<string> {

    try {

      const result = await this.education.create({ userID, degreeID, fieldID, instituationID, year_of_completion });

      return result.ID

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async GetExperience(userID: string): Promise<Candidate_Experience[]> {
    try {

      const result = await this.user.findOne({
        where: { ID: userID },
        relations: { CandidateExperience: { Category: true } }
      })

      if(result?.CandidateExperience === undefined)
        throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')


      return result?.CandidateExperience

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }


  //#######################################################################
  async GetEducation(userID: string): Promise<Education> {
    try {

      const result = await this.education.findOne({
        where: { userID },
        relations: { Degree: true, Field: true, Instituation: true }
      })

      if(result === null)
        throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

      return result 

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }

  async GetSkill(userID: string): Promise<UserSkill[]> {
    try {

      const result = await this.user.findOne({
        where: { ID: userID },
        relations: { Skill: { Skill: true } }
      })

      if (result?.Skill === undefined)
        throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

      return result?.Skill

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }


  //#######################################################################
  async GetCertificate(userID: string): Promise<Certificate[]> {
    try {

      const result = await this.user.findOne({
        where: { ID: userID },
        relations: { Certificate: true }
      })

      if (result?.Certificate === undefined)
        throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')


      return result.Certificate

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }

  //#######################################################################
  async UpdateProfile(
    userID: string,
    employed: boolean,
    firstName: string,
    lastName: string,
    dob: Date,
    experience: number,
    gender: string,
    classificationID: string): Promise<User_Profile> {


    try {
      const res = await this.profile
        .createQueryBuilder()
        .update()
        .set({
          employed,
          firstName,
          lastName,
          dob,
          experience,
          gender,
          classificationID
        })
        .where("ID = :ID", { ID: userID })
        .returning("*")
        .execute()

      return res.raw
    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

}

