import { Entity, PrimaryGeneratedColumn, Column, OneToMany } from "typeorm"
import { Job } from "./job";

@Entity()
export class Classification {

  @PrimaryGeneratedColumn("uuid")
  ID: string;

  @Column({unique: true})
  name: string;

  @OneToMany(() => Job, Job => Job.Classification)
  Jobs!: Job[];

}
