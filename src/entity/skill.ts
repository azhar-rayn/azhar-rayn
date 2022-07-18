import { Entity, PrimaryGeneratedColumn, Column, OneToMany } from "typeorm"
import { UserSkill } from "./user_skill";

@Entity()
export class Skill {

  @PrimaryGeneratedColumn("uuid")
  ID: string;

  @Column({ unique: true })
  name: string;

  @OneToMany(() => UserSkill, userSkill => userSkill.Skill)
  userSkill: UserSkill[]

}
