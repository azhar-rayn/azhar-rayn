import { Entity, PrimaryGeneratedColumn, Column, ManyToOne, JoinColumn, OneToOne } from "typeorm"
import { Skill } from "./skill";
import { User } from "./user";

@Entity()
export class UserSkill {
  @PrimaryGeneratedColumn("uuid")
  ID: string;

  @Column("uuid")
  userID: string;

  @Column("uuid")
  skillID: string;

  @Column()
  level?: string;

  @ManyToOne(() => User, user => user.Skill)
  User!: User;

  @ManyToOne(() => Skill, skill => skill.userSkill)
  Skill!: Skill;

}


