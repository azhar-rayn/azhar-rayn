import { Entity, PrimaryGeneratedColumn, Column, OneToMany } from "typeorm"
import { Education } from "./education";

@Entity()
export class Degree {

  @PrimaryGeneratedColumn("uuid")
  ID: string;

  @Column({ unique: true })
  name: string;


  @OneToMany(() => Education, education => education.Degree)
  Education!: Education[];

}
