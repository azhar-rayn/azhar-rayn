import { Entity, PrimaryGeneratedColumn, Column, OneToMany } from "typeorm"
import { Education } from "./education";

@Entity()
export class Education_Field {

  @PrimaryGeneratedColumn("uuid")
  ID: string;
  
  @Column({unique: true})
  name: string;


  @OneToMany(() => Education, education => education.Field)
  Education!: Education[];
}
