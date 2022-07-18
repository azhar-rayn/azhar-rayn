import { Router } from 'express'
import { UserService } from '../services';

const userRouter = Router();
const userService: UserService = new UserService();

userRouter.get('/signup', async (req, res) => {
  const { email, phone, source, password } = req.body
  const result = await userService.SignUp({ email, phone, source, password })
  res.json(result)
})


export default userRouter;
