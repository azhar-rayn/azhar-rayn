import { Entity, PrimaryGeneratedColumn, Column, OneToMany } from "typeorm"
import { Candidate_Experience } from "./candidate_experience";

@Entity()
export class Category {

  @PrimaryGeneratedColumn("uuid")
  ID: string;

  @Column({ unique: true })
  name: string;

  @OneToMany(() => Candidate_Experience, CandidateExperience => CandidateExperience.Category)
  CandidateExperience!: Candidate_Experience[];
}
