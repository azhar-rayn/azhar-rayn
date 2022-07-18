import { Router } from 'express'
import { UserSkillService } from '../services';

const UserSkillRouter = Router();
const service: UserSkillService = new UserSkillService();

UserSkillRouter.get('/:userSkillID', async (req, res) => {
  const { userSkillID } = req.params
  const result = await service.GetSkill(userSkillID)
  res.json(result)
})

UserSkillRouter.put('/:userSkillID', async (req, res) => {
  const { userSkillID } = req.params
  const { skillID, level } = req.body
  const result = await service.UpdateUserSkill(userSkillID, skillID, level)
  res.json(result)
})

UserSkillRouter.delete('/:userSkillID', async (req, res) => {
  const { userSkillID } = req.params
  const result = await service.DeleteSkill(userSkillID)
  res.json(result)
})


export default UserSkillRouter;
