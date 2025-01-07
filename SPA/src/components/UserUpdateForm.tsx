import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form"
import { z } from "zod"
import { Button } from "@/components/ui/button";
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
  } from "@/components/ui/form"
import { Input } from "@/components/ui/input"
import { useNavigate } from "react-router";

const formSchema = z.object({
    username: z.string().min(5).max(30),
    firstName: z.string().min(3).max(30),
    lastName: z.string().min(3).max(30),
    email: z.string().min(10).max(50).email(),
})

const UserUpdateForm = ({user} : {user : User}) => {
    const navigate = useNavigate();
    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
        defaultValues: {
            username: user.username,
            firstName: user.firstName,
            lastName: user.lastName,
            email: user.email,
        },
    });

    const onSubmit = async (values: z.infer<typeof formSchema>) => {
        const requestBody = {
            firstName: values.firstName,  // Correctly accessing values from the form
            lastName: values.lastName,
            username: values.username,
            email: values.email,
            password: user.password,
            userRole: user.userRole,
        };


        const respone = await fetch(`/api/users/${user.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestBody),
        });

        if (!respone.ok) {
            console.log('Dodac wyjatek');
        }
        navigate('/');
    };

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)}>
                <div className="mb-5">
                <FormField 
                    control={form.control}
                    name="username"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Nazwa uzytkownika</FormLabel>
                            <FormControl>
                                <Input placeholder="" {...field}/>
                            </FormControl>
                        </FormItem>
                    )}
                    />
                    </div>
                    <div className="mb-5">
                    <FormField 
                    control={form.control}
                    name="firstName"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Imie</FormLabel>
                            <FormControl>
                                <Input placeholder="" {...field}/>
                            </FormControl>
                        </FormItem>
                    )}
                    />
                    </div>
                    <div className="mb-5">
                    <FormField 
                    control={form.control}
                    name="lastName"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Nazwisko</FormLabel>
                            <FormControl>
                                <Input placeholder="" {...field}/>
                            </FormControl>
                        </FormItem>
                    )}
                    />
                    </div>
                    <div className="mb-5">
                    <FormField 
                    control={form.control}
                    name="email"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Email</FormLabel>
                            <FormControl>
                                <Input placeholder="" {...field}/>
                            </FormControl>
                        </FormItem>
                    )}
                    />
                    </div>
                    <div className="flex justify-center">
                    <Button type="submit">Zatwierdz zmiany</Button>
                    </div>
            </form>
        </Form>
    );
};



export default UserUpdateForm;