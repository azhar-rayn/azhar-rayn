import { Router } from 'express'
import { UserCertificateService } from '../services';

const UserCertificateRouter = Router();
const service: UserCertificateService = new UserCertificateService();

UserCertificateRouter.get('/:certificateID', async (req, res) => {
  const { certificateID } = req.params
  const result = await service.GetCertificate(certificateID)
  res.json(result)
})

UserCertificateRouter.put('/:certificateID', async (req, res) => {
  const { certificateID } = req.params
  const { topic, type, instituation, date } = req.body
  const result = await service.UpdateCertificate(certificateID, topic, type, instituation, date)
  res.json(result)
})

UserCertificateRouter.delete('/:certificateID', async (req, res) => {
  const { certificateID } = req.params
  const result = await service.DeleteCertificate(certificateID)
  res.json(result)
})


export default UserCertificateRouter;
