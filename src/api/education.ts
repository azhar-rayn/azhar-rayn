import { Router } from 'express'
import { UserEducationService } from '../services';

const UserEducationRouter = Router();
const service: UserEducationService = new UserEducationService();

UserEducationRouter.get('/:educationID', async (req, res) => {
  const { educationID } = req.params
  const result = await service.GetEducation(educationID)
  res.json(result)
})

UserEducationRouter.put('/:educationID', async (req, res) => {
  const { educationID } = req.params
  const { degreeID, categoryID, fieldID, instituationID, year_of_completion } = req.body
  const result = await service.UpdateEducation(educationID, degreeID, categoryID, fieldID, instituationID, year_of_completion)
  res.json(result)
})

UserEducationRouter.delete('/:educationID', async (req, res) => {
  const { educationID } = req.params
  const result = await service.DeleteEducation(educationID)
  res.json(result)
})


export default UserEducationRouter;
