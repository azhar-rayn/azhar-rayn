import { Router } from 'express'
import { UserExperienceService } from '../services';

const UserExperienceRouter = Router();
const service: UserExperienceService = new UserExperienceService();

UserExperienceRouter.get('/:experienceID', async (req, res) => {
  const { experienceID } = req.params
  const result = await service.GetExperience(experienceID)
  res.json(result)
})

UserExperienceRouter.put('/:experienceID', async (req, res) => {
  const { experienceID } = req.params
  const { title, categoryID, companyName, city, experience } = req.body
  const result = await service.UpdateExperience(experienceID, title, categoryID, companyName, city, experience)
  res.json(result)
})

UserExperienceRouter.delete('/:experienceID', async (req, res) => {
  const { experienceID } = req.params
  const result = await service.DeleteExperience(experienceID)
  res.json(result)
})


export default UserExperienceRouter;
