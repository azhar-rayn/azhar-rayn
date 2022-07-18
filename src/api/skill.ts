import { Router } from 'express'
import { SkillService } from '../services';

const SkillRouter = Router();
const service: SkillService = new SkillService();

SkillRouter.get('/list', async (req, res) => {
  const result = await service.ListSkill()
  res.json(result)
})

SkillRouter.post('/', async (req, res) => {
  const { name } = req.body
  const result = await service.AddSkill(name)
  res.json(result)
})


export default SkillRouter;
