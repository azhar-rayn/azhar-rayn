import { Entity, PrimaryGeneratedColumn, Column } from "typeorm"

@Entity()
export class Address {

  @PrimaryGeneratedColumn("uuid")
  ID: string;
  @Column()
  country: string;
  @Column()
  city: string;
  @Column({ nullable: true })
  street?: string;
  @Column({ nullable: true })
  house?: string;
  @Column({ nullable: true })
  lat?: number;
  @Column({ nullable: true })
  long?: number;
  
}
