// All Business logic will be here

import { UserRepository } from "../repository";
import { User } from "../entity";
import { GeneratePassword } from "../utils";

export class UserService {

  private repository: UserRepository

  constructor() {

    this.repository = new UserRepository();

  }


  async SignUp(userInputs: User) {

    const { email, password, phone, source } = userInputs;

    try {

      let userPassword = await GeneratePassword(password);

      const user: User = {
        email, password: userPassword, phone, source
      }

      const existingUser = await this.repository.CreateUser(user);

      return existingUser;

    } catch (err) {

      console.log(err)

    }

  }
}