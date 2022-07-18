// All Business logic will be here

import { Candidate_Experience, Education, UserSkill, Certificate, User_Profile, Address } from "../entity";
import { UserProfileRepository, AddressRepository } from "../repository";
import { APIError, STATUS_CODES } from "../utils/app-errors";

export class UserProfileService {

  private repository: UserProfileRepository
  private addressRepo: AddressRepository

  constructor() {

    this.repository = new UserProfileRepository();
    this.addressRepo = new AddressRepository();

  }

  //#######################################################################
  async AddExperience(userID: string, title: string, categoryID: string, companyName: string, city: string, experience: number): Promise<string> {
    try {
      return await this.repository.AddExperience(userID, title, categoryID, companyName, city, experience);
    }
    catch (err) {
     
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')
      
    }
  }


  //#######################################################################
  async AddSkill(userID: string, skillID: string, level: string): Promise<string> {

    try {

      return await this.repository.AddSkill(userID, skillID, level);
    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }


  //#######################################################################
  async AddCertificate(userID: string, topic: string, type: string, instituation: string, date: string): Promise<string> {

    try {

      const _date = new Date(date)

      return await this.repository.AddCertificate(userID, topic, type, instituation, _date);

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async AddEducation(userID: string, degreeID: string, fieldID: string, instituationID: string, year_of_completion: number): Promise<string> {

    try {

      return await this.repository.AddEducation(userID, degreeID, fieldID, instituationID, year_of_completion);

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }

  }

  //#######################################################################
  async GetExperience(userID: string): Promise<Candidate_Experience[]> {

    try {
      
      return await this.repository.GetExperience(userID)
    
    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }

  //#######################################################################
  async GetEducation(userID: string): Promise<Education> {

    try {

      return await this.repository.GetEducation(userID)

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }

  //#######################################################################
  async GetSkill(userID: string): Promise<UserSkill[]> {

    try {

      return await this.repository.GetSkill(userID)

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }

  //#######################################################################
  async GetCertificate(userID: string): Promise<Certificate[]> {

    try {

      return await this.repository.GetCertificate(userID)

    }
    catch (err) {

      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
  }

  //#######################################################################
  async GetProfile(userID: string): Promise<any> {

    try {

      const certificate = await this.GetCertificate(userID)
      const education = await this.GetEducation(userID)
      const experience = await this.GetExperience(userID)
      const skill = await this.GetSkill(userID)

      const updatedCertificate = certificate.map((val) => {
        const { date, instituation, topic, type } = val
        return { date, instituation, topic, type }
      })

      const updatedExperience = experience.map((val) => {
        const { Category, city, companyName, experience, title } = val
        const { name } = Category
        return { city, companyName, experience, title, category: name }
      })

      const updatedSkill = skill.map((val) => {
        const { level, Skill } = val
        return { level, skill: Skill.name }
      })

      return {
        education: education ? {
          degree: education?.Degree.name,
          field: education?.Field.name,
          instituation: education!.Instituation.name,
          year_of_completion: education!.year_of_completion
        } : undefined,
        certificates: updatedCertificate,
        experiences: updatedExperience,
        skills: updatedSkill,
        ...this.repository.GetProfile(userID)
      }
    }
    catch (err) {
      
      throw new APIError('API Error', STATUS_CODES.INTERNAL_ERROR, 'Internal Server Error')

    }
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
    address: Address,
    classificationID: string): Promise<User_Profile> {

    const { ID } = await this.addressRepo.AddAddress(address)

    return await this.repository.AddProfile(
      userID,
      CNIC,
      employed,
      firstName,
      lastName,
      dob,
      experience,
      gender,
      ID,
      classificationID
    )
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


    return await this.repository.UpdateProfile(
      userID,
      employed,
      firstName,
      lastName,
      dob,
      experience,
      gender,
      classificationID
    )
  }

}