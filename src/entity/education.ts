import { Entity, PrimaryGeneratedColumn, Column, OneToOne, JoinColumn, ManyToOne } from "typeorm"
import { Degree } from "./degree";
import { Education_Field } from "./education_field";
import { Instituation } from "./instituation";
import { User } from "./user";

@Entity()
export class Education {

  @PrimaryGeneratedColumn("uuid")
  ID: string;

  @Column("uuid", { unique: true })
  userID: string

  @Column("uuid")
  degreeID: string

  @Column("uuid")
  instituationID: string

  @Column("uuid")
  fieldID: string

  @OneToOne(() => User)
  @JoinColumn()
  User: User;

  @OneToOne(() => Instituation)
  @JoinColumn()
  Instituation: Instituation;

  @Column()
  year_of_completion?: number;

  @ManyToOne(() => Degree, degree => degree.Education)
  Degree!: Degree;

  @ManyToOne(() => Education_Field, field => field.Education)
  Field!: Education_Field;

}
