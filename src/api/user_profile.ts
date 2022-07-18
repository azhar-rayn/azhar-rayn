import { Router } from 'express'
import { Address } from '../entity';
import { UserProfileService } from '../services';

const router = Router();
const service: UserProfileService = new UserProfileService();

router.post('/:userID/experience', async (req, res) => {

  const { title, categoryID, companyName, city, experience } = req.body
  const { userID } = req.params
  const result = await service.AddExperience(userID, title, categoryID, companyName, city, experience)
  res.json(result)

})


router.post('/:userID/skill', async (req, res) => {

  const { level, skillID } = req.body
  const { userID } = req.params
  const result = await service.AddSkill(userID, skillID, level)
  res.json(result)

})


router.post('/:userID/certificate', async (req, res) => {

  const { topic, type, instituation, date } = req.body
  const { userID } = req.params
  const result = await service.AddCertificate(userID, topic, type, instituation, date);
  res.json(result)

})


router.post('/:userID/education', async (req, res) => {

  const { degreeID, fieldID, instituationID, year_of_completion } = req.body
  const { userID } = req.params
  const result = await service.AddEducation(userID, degreeID, fieldID, instituationID, year_of_completion);
  res.json(result)

})


router.get('/:userID/experience', async (req, res) => {

  const { userID } = req.params
  const result = await service.GetExperience(userID);
  res.json(result)

})

router.get('/:userID/skill', async (req, res) => {

  const { userID } = req.params
  const result = await service.GetSkill(userID);
  res.json(result)

})

router.get('/:userID/certificate', async (req, res) => {

  const { userID } = req.params
  const result = await service.GetCertificate(userID);
  res.json(result)

})

router.get('/:userID/education', async (req, res) => {

  const { userID } = req.params
  const result = await service.GetEducation(userID);
  res.json(result)

})

router.get('/:userID', async (req, res) => {

  const { userID } = req.params
  const result = await service.GetProfile(userID);
  res.json(result)

})


router.post('/:userID', async (req, res) => {

  const {
    CNIC,
    employed,
    firstName,
    lastName,
    dob,
    experience,
    gender,
    country,
    street,
    city,
    house,
    lat,
    long,
    classificationID } = req.body

  const { userID } = req.params

  const address: Address = {
    country,
    street,
    city,
    house,
    lat,
    long
  } as Address

  const result = await service.AddProfile(
    userID,
    CNIC,
    employed,
    firstName,
    lastName,
    dob,
    experience,
    gender,
    address,
    classificationID);

  res.json(result)

})



router.put('/:userID', async (req, res) => {

  const {
    employed,
    firstName,
    lastName,
    dob,
    experience,
    gender,
    classificationID } = req.body

  const { userID } = req.params

  const result = await service.UpdateProfile(
    userID,
    employed,
    firstName,
    lastName,
    dob,
    experience,
    gender,
    classificationID);

  res.json(result)

})


export default router;
