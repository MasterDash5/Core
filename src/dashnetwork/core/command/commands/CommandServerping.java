package dashnetwork.core.command.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.Arguments;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.command.CommandSender;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CommandServerping extends CoreCommand {

    public CommandServerping() {
        super("serverping", PermissionType.ADMIN, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (args.length > 0) {
            String[] split = args[0].split(":");
            String address = split[0];
            int port = 25565;

            if (split.length > 1) {
                try {
                    port = Integer.valueOf(split[1]);
                } catch (NumberFormatException exception) {
                    MessageUtils.message(sender, "&6&l» &That is not a valid integer");
                }
            }

            String hostname = address + ":" + port;

            MessageUtils.message(sender, "&6&l» &7Pinging &6" + hostname + "&7...");

            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(address, port), 5000);

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                // Handshake

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                DataOutputStream handshake = new DataOutputStream(bytes);

                handshake.writeByte(0x00);
                handshake.writeInt(340);
                handshake.writeInt(address.length());
                handshake.writeBytes(address);
                handshake.writeShort(port);
                handshake.writeInt(1);

                out.writeInt(bytes.size());
                out.write(bytes.toByteArray());

                // Status request

                out.writeByte(0x01); // Size
                out.writeByte(0x00);

                // Status reponse

                in.readInt(); // Size
                in.readInt(); // Id

                int length = in.readInt();
                byte[] data = new byte[length];

                in.readFully(data);

                String json = new String(data, StandardCharsets.UTF_8);

                // Ping

                out.writeByte(0x09); // Size
                out.writeByte(0x01);
                out.writeLong(System.currentTimeMillis());

                in.readInt(); // Size
                in.readInt(); // Id

                // Close

                handshake.close();
                bytes.close();
                out.close();
                in.close();
                socket.close();

                // Json time

                JsonObject main = new JsonParser().parse(json).getAsJsonObject();
                JsonObject version = main.get("version").getAsJsonObject();
                JsonObject players = main.get("players").getAsJsonObject();
                JsonElement samples = players.get("sample");
                JsonElement description = main.get("description");
                JsonElement favicon = main.get("favicon");
                List<String> playerlist = new ArrayList<>();

                if (samples != null && !samples.isJsonNull()) {
                    for (JsonElement player : samples.getAsJsonArray()) {
                        playerlist.add(player.getAsJsonObject().get("name").getAsString());
                    }
                }

                String icon = "iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAYAAADDPmHLAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAZdEVYdFNvZnR3YXJlAFBhaW50Lk5FVCB2My41LjVJivzgAAAyRUlEQVR4Xu3dZZPlyNGG4fMvHGZmZmZmZmZmZmZmZmavmZk5bP8fvXEp4p7IVfRMj8e7M/tOnw8K9ZFUlPkkVFZW9e6b3/zmsr+OLg12e+YfXebj/R4AR1wD7gGwB8DRVoFH3QTuNcBeA+w1wFHWAnsNsNcAew2w1wBHXAr2ANgD4MhGQ/c+wBEH/x4AewDsncAj7QN861vfWvbX0aXBbs/8o8t8vN8D4IhrwD0A9gA42irwqJjAH/zgB8tPf/rT5de//vXypz/9afnHP/6x/Oc//9mbgLMZAJj9l7/8Zfn3v/+9Mvuga28CzmITEPP3ADiLmXwiDXaY9O9NwFkOjD0AznIGH+a//Pa3vz2h/V81wDnnnLPsr7OTBpzAgxy/v/3tb+tM4Pe///0eAGcz+H/+858fY7S/TQNNB+e11wBniQb8zne+s/zoRz9afvGLXyxU/5///OeFpG8ZvgfA/3OGH8Tof/7zn8u//vWvA69f/epXJwTBXgNcgAHxve99b8HA3/3ud6tEn4jRxwOAiN+JtMAFDgBvf/vbl3vf+97La1/72uVNb3rT8rrXvW559atfvXz4wx8+cs7qz372s+NK9vEYftBzADoeCC4wAPjSl760vOMd71hue9vbLre85S2Xm9/85sstbnGL5UY3utFym9vcZnnqU5965ADApv83jD7etzSHug4CwRkHwGc/+9nlBS94wSr1N7zhDZdb3/rWy81udrOV8be73e1WINz0pjddHvWoRy1PfOITl3e+851HBgjnBQA4gn/9618PnAEAxBkHABVP4jGcxF/vetdbbnCDG6xMx3y/b3zjG6+gAI6HPvShy2te85ojAYKTBQA7j8l/+MMflt/85jeLKd9PfvKT5Yc//OGh1+7b3/72cqauz3zmM8u97nWvldmY7H6Tm9xkBYDrjne84wqM61znOqt2YArufOc7Lw9/+MPPWJ9PJ61+/OMfHzMB1Dgm//GPf1ydwl/+8pcLH+FkmHyib84oAN7//vcvz3zmM1cpx2BMnxrAsy4AAYg73elOy8tf/vIjAYDvfve7qzQDwv/K6OOVP6MAoAHuc5/7LNe//vVX5pP2gEAT3P72t1+lv+c0BDNAA3zkIx9ZzBiSyI997GNnJSjM+zGPOTg/rjMKABqAY5eKJ/2YTdqve93rHjMLnt3qVrdaTcBd73rX5Z73vOcKGNoAgB7xiEcsD3zgA5c3vvGNZyUIxAP+F+bTIBy+73//++tFswAW4TmjADADePSjH31MzdMEmQB3TAcEF4bTDrRAfwOKbzx//OMfv7z0pS89KwGAURh3IhDQElsmA85hPssZBQC1bXqH2VT+Va961eXqV7/6sesqV7nK+jcNAQRmCy4M95s5MHMACPGDpz3taYcOWLxBu29+85uXV77ylcv73ve+Q8tMIn70ox9dPv3pT6/lvva1ry3Pfe5zVy32gQ98YK2TaTqM6Kf6HghiMuZOST7VOs8oAPgAL3nJS9YpHoZi9rWuda3lGte4xnLNa15zBQQQZCJIO6CYHgIB5tMCNAd/4RnPeMZCq0SMD37wg8sb3vCG5YUvfOHKJLGGu9/97ss97nGP5Q53uMMaZ3jkIx+5AuGrX/3qCRnnvf4+5znPWesARO3rj9/qAuZnPetZ5xsATpXJJyp3RgFQxxCWKcBIjL3a1a62AsF17Wtfe/ULSLkpY1Lv7lngYBpogYc85CELKSWd/AOMuctd7rLc7W53W3+rw2U6aVYhrvCwhz1sNSFvfetbl0984hNrRPLjH//4uRgJAJxP/gem6yfgatOdP/LgBz94ecxjHrO23dhIKRtsyiaubw3eVM7c3Zo8qT4/GHuydV4gAFBnOXIcPdoACK50pSuthC4sTAMUI5hTRyDBBFLpG8zAKIy/3/3ut0q+K6l/0IMetL5zcSp9f//733953OMet3jnGVAIONk3qH/vfe971+kn8ABmJkk/AFfb6tEeAJm+/f3vfz/XhenzssBj6fZMguACBQC29RWveMWqrjGfGSDZMTdfgQQ2PaQdchSZA2DBfBJONXd/wAMesGoAGoLU+wajvWc+vGMOqPGAQJqtQdBQTIu+6Q9Qalc/9C2zBLw0DhBpR2QuEGyZ7zcACOoAAS1xslJ7Xn63Mx04k9fnPve55Qtf+MK5+sCW8wNIO2I+5SlPWaWLpGM8ictcYDqJLHzsN6ZiLql2p+ppBrYfYzCJ6jZ9pAUAACAAw3sXICjPNJFq74pU1r57DmomAQi0RePQIEBwEPM9k7QJAF00wenmxRkDADvJ5loIetGLXrQ86UlPWiWfd82z5gAiMMZQu/4GAIymGfIROIkuGsM3pF4ZF9AAQCFkwGBmUv0Y3PeYTO2z49pTniYAGppH2+qnbbSN8V36EwCBBMD4HMZkenYQADC/sG4A8O1ZD4AvfvGLq8S/+93vXp7whCes0ochJJ2UYRCJNSMgVSS69YFmBRjelDF73EoihmMaCfS3ev1WJ6b4XUg559B32gYGF1PAJACEtl1JfeADhAkC/WAOSL8x5VzyITCYAyh+Ly8P+DmZ73nPe9YULs9M684IAHipp/PCeOqVlGAEz5okujwrLIzBJA+Rmwmk/oEjiQQQDMZMUovJnD3S71nt5BdQ+4EO473HbJfvldUnQPS3+rVLwjEdADio2tc/jHfXp2YrQPDYxz52BRAAWJnrEi/gTOaH8E1e9apXrTEEADgdvGBqmpnsTkeDsw2MRqAYQ1IBoXn11ssPAAhNvZp7kzS/Sb1yGEdym+KpMzWeGQAMjPUdxmM44Pib+scIfUp6vW/K2VrEVP8AQBNhPrBe8YpXPKYltMWscRyNnYS7+DtPf/rTV2DxQbTNaeVn8BcEef5XftAkmAtwNI7L1NPF7FhRdMkTcJ12ABhsxMUMhEcIxGarXVQuZrtIXJKP+QV/SGZTufve977H5voImw1O7ach2HdtYgCmA4f2/O3qu+b7fAYmQ30Ah8mk3/3yl7/8CgCS7/eVr3zl9R0Npt63vOUtxxgfACxeqcvV2kaRTILx+c9//lAAYK7LNBNzzSAwV0wh5h52j/lnBABUH8YhPEkpIJMGaK5Psor4pe4L+Wbnk2bSjnlN7QAiP0D9NAICp21y9poaZobU4ztA8a06AEC9AKpPGF10suil52YtQFmsQR4jVVvI9utf//pqFvTNuIpZNM01bu9MhQWNMNfGDsw1kziMqSfzfjL+jGkAUzzERVTRN0xIUgvKUMMIPkO/niEuhmIMifW9uki994jqPm08oCEsm65s0z/g6V3xAv3gAKZRlAEAgADW2sBomgnT1ed74/Dt8573vHNJ8Yc+9KF1rN6rg3ZjUlwxnxZoGmk84g7U9bxOhsGHfbMFgPpPuwkgBUmqQAsiY6QLg0kHomIGAiFUtjiABBjfI2oM9Lt6MIsq7pnvvDPHTxsU8Uv9+530qxMzaItAmraapok2E6cwLpdpLemVmsX7N6199rOfvfot5TfmxwBSaxkAwNRpw/pFzD+Mqcd7zyS4aI+mmTSKy8yDCXHtqKjTeVmgaTEG80kPFdw0DEE9w5SmhFRz6j4V6zsSn1SnHWgNf5c7ADxsbsAgpcDgu6Z97gCD+fwT39Yv3wIC3yWtgZnNRIDZfB+ozG4ABgBE+VxWH2mVmeBa9BDTaYQJBOB629vedlwAqDPmxlhgw1hTzRh7svfTDgBgQ2AMzAtHeAzHTFKL6AhKQxS0UQYQCt9ihm8rM8vSEEDgUr55P1Col0QX+HHHxNT9nFICQ9PJymAkJnH4mADfM2VMmzttYEVQvIMUf/KTn1yBpY1sf9lPM/GlTCjaQeYz24/BSS1Qkdzz+jqtADANEu0j7aQ+1Z/T5jkGJXUt3WYy8voxpfBu0osxnje99Dep9ruFmnwE0q9O9eU/tAjkOaamkfSFZAMAALorzzHFQGMQMcT4QGQ8chO+8pWvrHEA7wG04BH/wd/FEZi5VhW1J05wXjP6ePXtqA8vTS3yWs8vkyDyhegISSqS7uwz4gOA5y3itHSLQAV2sus5ey3oAIWrqVYACwyVV25GC2u3iGGBobQNpueXpC20Q5W3pAxAvgNwc30BJ8CRpmYfP7VOAxiPcvqC8UAp9C1uYOpooSdmTak/3zTAXIxI5ZiGSD86L4EgXYtUuUgIW8m2YgZCx5Tm5J4hdsu4RfKy5QhYkCcAqMPfRfR8m08QOJr7q88z33MM/U76SXJrBMCAkfrhOeBibprKd55hehFN9dFwnisriaQ8/W984xtrGBhgxAyYpQI23Qsbb5+fH793WwDM34IMxa9LKDzVO/tIckkJAiLQlPz8gnwDzNlKcPYY0/L+C+f6HpMwDMBcpM13qXn3zEfagLZIG7UqqJ7MRkklGEVD+F28P83iuRkAZw/zOYyZEN+rj4R/6lOfOpaYCQBMgPYJ23/D3IMAcqqg2WHyiUAwlypPlfnKWfhBsJwpDCrY4rmrKF0S3bSv2P4sj7Akzj1nr2fVHfOrGxMDQHEF98LAreXXrt+AWtwCc9VdIArolNdvGg0ImAD1peWUUR44ykriEwAgDQCU5frx3DEyj97fp3KdTPm+2eVl8gVcBwGirJVTBQBPuAggNYooEZlmQJiImSlIA0xwKIPJJLapYvl4CAoQefU5ltR635DqnMLCz35jWPH57H8OpTvbrT80StPQHDvawHOqvvuTn/zkY8GqprjlFLReUZSTIxntt/P1eHMYj+b7bZnDfu9EnSxP1ontPUAcdtLEid7b6p3kUX2IRcoxh7qc6h8YStLAuKZxLfbE5ACAoMf7G9GLIZQbqB+ZAs/0Q53NNCqjT2mM1hbUVRg757TVxNYZ9DNfx1ha62Am/A0c5SYCFSCYMrZgM+8A0W8BneNdvjke/w57vhOz5qHODzG9i0dazvmpgsBK11yPbyEmYhWQyXnDlObxZfdgMkbNe38X60/1Y055gO4YgQHberWRIwgYymMk5nuOYc0MgAZI3F3GQFv5W71FELVHw2USWmlkGmg7gA9wmRNagpN4EAi2z7Y+Wnw6jNHHe7/jnPDQdeAg9c8uxXjr1gBjnurAhpMFhLRrRMWIImwt4RZqRewYlC8wEzZitu/y2FtLyBdIExTqbUYRGLzXhuhgjMNE/ZprBjmIxQp8XyCqpWwMn6HmFpS0iclsfrEDbZZnCBzGxxH2XX4B2pp1TeE7HX/vMMdFSqmibbwYMKRZy98HFteLX/zi5WUve9kKBlErGbPWsduAuN3MyAQgAnXJBygti3QhHKKI+hXpQ/iCQTEbMTESgYvsZQ6o1LJ/Y3orfc0K1ONd4JrrBuUIpilapmautFnsopmFetSb7xITMdVzY2nlr4Uiz3IiS0BlKjJhBZxkFJ+MU35efbOzOxfzIVC2zkG7SB3V8vznP39d6LDa5RK88AwQbLx417vetQJoW16OWwxFSMzHnNQnRniPUBGIdOVJx1DMVnYGhhDQd77HrFR/TM80+K2NpmQ5ejE0SRaG1R8S713TvLKGaQl/6wvgAnDOYmofI0m/Wc+MKtJ004TxBVpBLAXN+MQMrCCeX7uBt/XudISEukjyQfvPqHzMxngg8HeXZ7SClOnXv/715yqP+b5D8CJ0pAuxpu31HkERZDK++TzCNXMo5t86QHN3BJ7z/0Aw/YEA0HdJeo5hTqE6AQ8zgQFwmymkxfQnreY7vydD/S7fQV2YjMH+bg9C6w76nvYBZDxB8/9lQ+jJlt1ZxKABAIAzSAtQ66S5SjAWI0l9GiAwlNXLLMh4mQ0LfDSHbvBsYcwiIc0OStYoMTRCuWNYS74BoAii8sqmAVLPnimX9Mdk9eTpaz/PnuTTAK3a5UcUgsbcMpa01Rgwsaktn8TfZjmlk5dVZFxAn9kDePVUV/1lKtTTcThM68ky81S+27Ht4tUA4LgWYIA+mSlsuS3cflNNMR8Y/M4kuDMDypayBEAGUsgWQQ0WAHLemnq1yINALRG7J8WtpmFS6/wt/hSIyQeYdjowYHrJFy0WBSTvSsjA4GYGrVRy+khn2cTFKXImvTemnDyMzgkMxMDifWADEPWpY+uj6H8xBwtKVhZlE0XXU71bY2Ba8NjyNfryU3aY76Gs1Lx8TqGERo1hPunGcEzG7DRBfzMB3ilPe1gDtzpWsIf0Zcuz7wVIZvy+OXlBHOUyH9npTEm5/hiKsIicRGfjU/mYWTKGe/n9bTtvw0mBInWXVKqOnMFiCqRVX33XfkTjxXiq31iNczJ45jv0DSYok/TnMGqPQ0lQ0AkIxGpOlfnKWYjTf+alzGvL0jteZz6AjRrUPUDIZLEhklTTEgCA8bJb/M151DEmJG0ACGkQ6CoCV7i1jRiTeG37IqFt/y6BI5VZ2LbYQO9zwJKi8vkwPPVPetVr0KWYSeaQyVsuv3th2bZ3AW97ElP/ypdHUIBKnzALAIDQ1c4iDGzK13pD3r4+Yki+gufFIIwrP4G2AB5OJbNgyk4D49VMNz/sbxoAv9RnXLKP0HyNAwCANCTMyx+gGWTvaJB0axTzk35/q9BFE7iaKmJICzct2ng25/qYazkU4d0xQZJFuXKYTDUXz/d9EcLCvfkP3pHEAkvFDFK5AaCtXdopo1dSh0WZjp9JqoFu2vye0zIxSr0AgOkAQM17x+FzlX1EGNqGBlh+G5f+Yy4NMH0TY29mlL9SIgut0U5mpsHqYlnHJ7pzyNu30EksqwZo4cUgTPcCQCd1QhozAAhMAe0Q89MCASRNUbZOjlurgA1Um9ldDJAN084bktlKXGlexd/z0oGpJdtMQoBrmTm1StICgLv2tNHld+ncPP7q0ccOrWzZujQybZSs0lSUagUCvzEbAFr6xsR2PbexxBoARtS3QOw3Rkk7v8IVrrAmnzJR7Ub2jllorYRQHQYCQGFq1I3pmUBaYFd+ukFhOjseCPgFQJEfgMHeYTjmU/9TAxh0Yd2886Z72cUSIQx+5vx3OIRnVBQJzAHzd45RfgJvGRDKLwQSbZfGnQnwvtz7dhVjusHH/LaX51gqq58lqGqnTZ+FiEsNa5aSisb8Mp7aYQRINEwHXxgrhkoxx5ROQmsLXIdktNcAGNBLH2gGvMocAueXv/zlNZV8e1m8y3kvi2m7pW1XQqIBU2ECOy7zehkqfAKS7wIEGsB7AHDlGzANhTnLpMmR8jtCYaBBG1D59EkFphg8hiFa0brW85PKQspFAkv8yAQUvdNOeXeIb/AxPTNgU6mNHZ4zCzlIGNyMAFMnwVsV1K+ieqSfjWZjMwOpd5olJtM8GGqs+qBPmUGAq7+Y3wbZBMW3aAAAza7QnH0/KAnUlL5zl7TX+UsAj87q3ZWf5kONUzEIaBDsfkGezADfACiYhHyAtAB1VECjsGvSlKZJCnVC2222QIxOB2lTCMK55pwfIzCg/MFiBJmbNAPmGEvgQkwq1YXp2vfMe2PHFJI5TydBNE6qMdUu4k8A0DAAQgPICqIFAZPK1ZektX64A1yqPUboj/En9Ze73OVWUPrtb/0GUGMy5vYuastU/aCcP+Zb/6n9NteUfh7fdyEEYzTmw/LWORvlq4n7k36VMgs0hHfNAtybO895PVC0t06j7epFXAMK6UmnQbv0C3GAshBskTfMmCtqGFQeYDOEdvRiqnYxOKIn8TEC030HGPpTsibCtfjjblxN51o/AHTMwPz2B7QzGABa8FFXsw99qT/ajiZoj9G+w/To1fb3fBW0ATQah0kwxTsoo8hSP9okdO1tLCF19QFU3hZrHUD8nAXSpwJeK7XPJ8g5BAK+AOmnFexrK/ZevL75P0BhYvvs21CZTQz1OuRvV4cvIFyrdy3TtrCTh5wjWJCo8DC7m+SrsxPIcgSNHaFL8W77eTuB9Sd/pByCAlSFmJvKCa7QAk4UAQa/W+hqhmFMzT5IYnTwLI1YPwEkumQ29N+zDtLWtjZM1w/KHCKsBKhj9dptPc812OV8aaRTOToGBVIMHKKBALMBgaOI6aZ+jkXjfFgezkbPhE2SSmJjNmKnDlO3DRpRECN7nX0EIMxvMaWl14JKxe1bJQQAxHHP21evdiOi52kfatK7gEETZIaa/jUzaUZgjGUn6YdZQDudaItOPgOgDrNInc9ZyDRLwBgIASCNRFNd6lKXWmkToPMPgIHfIXI7V3Ot6nLYaaoO04rxzSpWDQCVDbwt1+1YKXDSShpU8wUKGAEExptjCijN3Pqklb1q42QgyKlJIhGmc37ygCE3c1SACFFb8883aEqYo4lheepAgJEI2W5ebeUHuF/2spc913u/2/XTRg5AaoeR9lrJbPGKmqeOtUvdGl/mrbZqt53F7Sh275vKXPrSlz5m+5VLG2QalGm6jE78DCaZX2amRjBpocxsmr3gV+csEsBdXnFTI4xAaMDIF3CHcrOE/ABaAACEfpmFmSLV1I+UYBimqFNHUnWY0klgOTnaNjiESDvUH2DEhPLvaZXm5WUClbEzQ7lNs7RHirR1mctcZiWqZ4ivrZiAaBjY8fWt6BW00X4+jncuZqHYANBM7QJQ7SgOBDG6d/qC6fqQ2tdXv5P4+uuuXN+iQRpaX/QfndrA2mwHf3Ny44Hfu6ZBqZs85zx0DGg3KxXP0bHqB20tHpGK1D5vvChXc/bW8XPW2uOftBtMXnmdjPEImlTn/QcwkldyiT5gApVHQksRVybwBbhJzPb3B7xsdOsDgAZEhaXL4ysu4J22mMr6iWmXuMQlVoAlrVvN45uEwPgveclLriAAzrQCsPrbXXlMTIMEZPVon++B9uhGG6IxOhircc+rGMg6DVRhRDDoPE0VdUZPKmMGRtjh1vCLipkFNA30d+vdpAcj+o2ICNsRb/rQwUsd+kQDdVBE6rdl39YG2P6SMkg/H6EYQqnb7h3hkiRGYATP7nqXj1BfjLuU8nIGMwctYnmu7RzRtnjF+ASL1AaM/JCYiekk/mIXu9hykYtcZOVBkcBmDPra95mFeWIJ8KNHMxq8rO1iDlOzBs5dyGiKlHPR0Sxzbl6kqt02ZdpSi2IDJX5i7mR24dW0RHF2gNLRiJUjBhjeYbw6C/CUdAEIJB/DAas0s3brtEKonDqygWkXhEj9IkTnEXcKWcCfSaSlohWOLjBUvkHLzMCAMeqN6QEvM+Nd5sc7zKUBAGAyuhkKgARUtMqENX3UnrHjTyeYbMPd2kH3zHvO+DoLCBlFiPK+WyUrbEu6ACNpK2mi3xhBK7DJBU8K1JTvRyJJCQZjdBKvH9DeNLRFISqWas3uKleKOKIDY4suM6kUY5L+JAoRXYiP0DmA2cm0QE5w8fmCQHNVsiBVaV+lkBsbrzuP35i0ySR0peox/MIXvvD6/OIXv/hyoQtdaAWBvy960YuuoPA3hhcLUJ/n6OU5euELOnduQRo95xcY9AkNm4oDkmuNA0wCJC3NCFo5cu//9iTBoT5JK/dvhlERr1W7vGfEmsGhuTLXjKGDIdrnT/prtzX61HHby0vZLncAeAowxYimgzliOb+N2zg7nrbM5ZicOWi9o1z/soiBvEWalr7bQFoCKdDpC8YCQjESYFVvPo3xZxp8V9ZyWtcMpOgnMBKMHEhjom3mDCOT0jQzrbsLqYUeESY15G8fFqbsaNQcrdRh3nAqeq7+5ROU369MHTbIuTrXQkWrVQaV+XBH8NLI3DvyjXpu5c13eepprByuvGpMLtCkD02Pmh97h5G0CxDltGJSIPSMKTIezOmQq2gEAJhfxFA93jX1RGNXG1Ca5RS/0EfagLRjnrLa6XwjoEcPoCfA+QXGwp/IX1A2IBVO7o6/61pASGlJVmWZgwIiBWNCHUJjrgFkD1u8QTwMIam+nz5Dx8BhPC0zo3OZg1bvOnixPL1W69qcSVrKG0AMQAC0JFI9OZAFmVpz0A9ENc58A0wCOvXre+sXMbepbt+VmNp738dI408jZaqMh6Bl893LOVTO31Q5kOlvjqHv9Ndz4AYu9ZfpVKwD/WYAKScwcHhX/KG/VxOQuvB3hyGaR+oEAvkbMVOt2cIIkHoDAIMwYCpKR8vaSVI6ebNFkKYkMx7RYcwlXEymZufL1K1POZglcXQMy9aXwKxiCMahjQDazMSzgjzl7yF8gG61s80iHWWTUGBMEdCmpNEIjZvyYWyaqL2CzXzUEVB8nylsi3r7KNC7WUXObVPappG0QrGDppR+rxoAgQrEqCgJ0GGE8RthcsIMusQOEtEatfeFa0Nny7KlV5eRk53FaOVbto0RMbXl2OlzlCcQ85TNFOWYVeeUyDmD8X1tZo70BTGzyQg9nd1UszpLXytQFq20651vywHwjTKECCgxA0NdM9RL0EgqdY0+ygAAX4GT6L1n+NXqKGHQ78L4ObkFidSlDc/zgTwr1rA6gSpLwqc0xGhECMWepc4wmXQIQPgb04rBp8aAp/WBph9lpOQEzmXowrvA1N+FYwuyFJTpfU5XGTZprha4InzPi+3PqWmh7sZWehkQGFtqF6CBEsP1sZRvvk/CkDNXokozifyBGdnLAc0spao7NyAAAIFnxTjQQJ/0pfyBFrbyMaYP0DS0uX8O4RoJVEHhUp1MdUJy9h8TME4HOhu/RMykPLtbfn5SW2KHNgqvFtvPwUrt5VxiThnFBXpKwkR0fkW/A0jS22FLOXAz2XTa9KaiyrtmlDJ1nEYqo3jmJxQrKP2t/IPa85wgpDHyeaYD2nQ7pzcV3rQRAEwHiwcU+CmRpNT0NI87TVHwiA+RuVGHvmVySsbZ5Xyk+vNo/QYGAOkdAHSEqwG2KbNl4HnGHuSnPdoYqgxgeNcaP2bPv1PxCIrgOVXZ9hmWTQVCcuDSP4gHqhZnCmVnGvwuy8j3/i6BRTnj1I72O/69sTet8145kl/qeuWasSShtFFqulwHzGzWUbid9GJaU0R/A4HZQKHgloZJuHamedHXZm5svPIuKl+5ltVLmwOGXU7btPVJH4LNZAsEzdOmHjMTZemKzVswap2+sjWcV0x6EQfB1Bko1NfGjrZRt1GknD+g4gBJiCg1KmkvDoFJmNxz7bZUHAiaueRDtI/ftzmf2XH9CyiFsYu1JwhpAmWNuz0Jxq4/xVH8nfboOVAknZjF5k/mAwQJbrZSgEc7yqXZmvYCku8DAS0CMMbhKoKqbzuMV7Dwayag9LDmrzlwVPI87rTMG/fUc7t4y3nP625hJacNwyJcK1jaD3jeYbTfgcV7oIjImJSWaj9A0zVlmr3EYP0MiKlrvxEl7WPMBavShO0syj8o4lfCSLuR802MVT+nRgOKMqKbMRij9jJp+k6AcoDVpwwApfrLHWyaDFDRVr1pVc/V3bRyLmyhg3Z2TYumyvV3q10VQoDCsBE2u5unWz5+uYElbWbbmy8XEVRn9su7fIPMRztlijQmderXeaqsaKB7zwoW1a8YXFuNNQKm2fzeqv/61TTWPdteZBQYixYmkWVD5T8YW2Dvm6J6fgOx38VZ8k2U874la9LdodlNpcVylNUf35Y6l2DobzGe6mFSgG4FQHYvxhbN0nhEyvYbWL6B9zlhOlDmb3YRAFx53klW9jz7njkI2RGi1K80iAFhdAcvtGMntdpBECVtqs9YKuPu25iSsxez5rsAX1i4vqmDpslh1qeyg1qG9az1A2P0rbGnmSqbFvCt+o27zGrtTO3T4g5VXq5GCzr5EAFYf+q/drVTVnJhd/UByk7miIROl79LAZf6JbvEb2v/LhnCnrn8fz0nf8oO8s3MSCltTJ6gPYaSR6pHBpHnMogqJ+FUAqPNi9pR3t3uYuU7xcRzu5WU17b/AKrPNqp4579xSIW2zd3vxuW9xEkp7L636dJGV32Q3yCdS3LLpIWMaO9Nc2VCSYH3nbLqcfdePcr5Xp89Mx7l2jdhpbS21aNMp6YALeAUaPJ3K51oGmjSDFLzOhOBwJWNVIq+PrRhp61k+uKSs+idOozJGHYaKecf0RBb56R+YbQKfePv9gv4W/6Zy5Zy37dzSDnfSxZxVCpmKdc3ThuxkUE533mHQL4DjtkG5nteH9wxWbveuXqnnAMYPQMOB1bUF7lx2tQn/fCtv9Xj78YaID2T5WSTq+/mzqj651vlvW8cttd5Rjj6ruN01Dfb1r9C3qS1bKamxLQk0GJ8fhnTBWg52+IwwKd97XTIh9/xVcaWd/qjz+30iqfrETGyexESoQAghiXxEQDjNeJbhJaNWnaQegISCTDgGBhh1UOCK6ed9iMiHOZqI4JipEv5QElLKN+/m5t9kqDqH1JhnIE3jpitTz2rf9qPgI3dM2UCTcRFp8prF8CBt2fKGbM+oBE6eKdfbagxDmUAIMcyR6/fzAa1jdYluXhHO0jFB4BAAACTwTRg9EM3m3z1td1d3k/Q7zxw6WwESF232TP0pJo9xwBqG6FT2xpGJPVIG5epGjHUmenwTLnMCYJQ97SDNjI/GNk/k0xKSRewaL+zi+pf4LJTxtmEiEwlBwwagnnQts0U55xzzkog4/fbXZk0AAZ7RmrShtEAs7XnHgBoAG24fO+3BE3l0ZeZibZoVfyjRbTpyTMNyjIHTVVpA+aj/4UMBMwSE0czqF9bQAEo1D0B1UdZ3NqmVfRFir8+HDslLGa3/dg9oodqajsJVQlGYoSBtoMov0HlvpmqusEjKtUeYUNnPkPmxL1vdXz6DGkAfcifqB7v9If9zCGkLhFrAkx7MTwQqU9bU2oADqAzNwCrjdSou3r4JIHXt4AgQYaEc8wwEkM5XyQaw/2tj83GqHvMLwLaHJ+9V1dmRP8AosismUxaIg1SEMu7QtFAR7t45/ku9R7Dc/amGkGoqVaVoUIDQHYyIlYWAJTz3fQDMJV6zT7FuHwGfchn6FtIhnLoVy8GAFf1+D67p7+crqZQvGlzcAGk/AJtYGy+Qu3pg2fqooLTdpheG8arjVSvu/7lxGYmAUDOQl4/ousHb785emsL3pne6XOLaYXeMbT8B0Jg/PoPOEUs53S6MHczt4JR0cO39Wl1ApM4f2fLDTwbRg0jjEFipOcIkmYIAAiRx07tJClAkPSqXz05kMrU5rT5McC3+Qzm2wZtyxowkErSWr/Ki8ccqhLhmtqaC9MCWx8jU5RmmOYpQDN1xqisvuZTpNHQQx+YpI7QMy4awUygKR1GpBGKibSdroCR/rL7NEXT0nwEofZMkbHSHk3JiyMUmwgYBfqa7jcVboq/i2hV3CDnlC91i9gxGBECAGZPkxFhpiMYk33H7iI0e5wD6nntTIcysFDzVF5zYwOg0tm5VDlmMlP+SYN+RmTftmGzrWzqdY4/n4IpIvUue+mV77AsdTp8CYhdNA5hsPXaGLQNzJ4RlDQMxvsbw1uW1o9yIgOmZ+UKtIOHam61sWBb4fSZda1sS/Zz6b4knZbMC3+XION5q7c7Uy+ddwaNATpHjkOEiR0KRRJyZACk577BRMDAIKqP8+EKEJwPDgc/wR1jfGf+7fKtb7TBmTFX7dRt89g2oJq3UuHZN4TxXl8AgVZQfp6ypS6MEE4uWUW5jnTp/xVzmtrZg1jsrfrU1X6/4gEluRbMUl+ayb3dtwVemupZ+Gl7trB7kTnPWo5v0aqEmELVheVLbmH3W0/oXaugZVzVvt+120pkeZ7q3xU7djeYQpqFLSPczP33rIUFKqzU6BZ2vCs8Woy7JA/1dMhSdRRdUxe11oKLdlrgqU3vMAaTMBJYmAV2tQMjZr3aV0flQ34OWNLQMXW+96zFohaqCk8XmClW3yKVcWmndZLCu0VVy0fwHCj63Rx/qvKAkAnwbfa8OEHteBcIetezys3/UTTB5O9jR8REiA4vKukhgBi4AZYYGZP8blGnOtof0EaRiIm4SUt7B3tXuzGo3T9Fyvqu+Hmp4QATaALLVHHeBYCYOLOWfesb7c+VyQlCbetX45z7BUpIyXOfBM5Dn9Ka6sfkybgcvphXTCDGT0b39wRbmiYNsF2wKwGnBJmAdy4AGAxGR/SJ/jSAO4Igoiub1D4AZWYsv+eVSSLLLmqZNgD0PAkt/czvlpExoESUpJ32ajfS1BbKNKVqPL2fQCile8v4LQ2AoOSXQDmTVKYkF4+Puf0uSQQzktqWrtn1lqSZmRahcmhbP5mLZy2i5ROkYSZQysWcwPPdagKSoBIZYkareIhg4JmHKQGete2rdKiZJ5DKrJ3qTmLKLG7Xr+fKJOHzrL/ad2+hqWNflWmVsDMJlZ2aS79c5S9qO9VdBo+yyrXLKC+9b+dpXR1gXX/VUS5FGm5r0hKQmIYJOWkxH3NLJ8/5KzkmAAQYz9WRc1z6eLOAFso8n4CrzK5tVd0RYA6ynLg0Q9oh05D6bG+Ae8xO5UeMHCfvp7/g7+ao2cxWtsrcqSyp6+8A2vp+Xr9vIlgZTgacWk0apj3V/rS1MSjCNW+ec/pZz1whbYo11Xb2vGmZb0gjh6wk2RJX0C16d+il3wlJOQMTeP5OG5cyl3BlEkvQiYZrQkjESn0UNGhAral7PlHYEmcEmeW3TAy1k2AIYcBNkZrGtDR9EJNaI6/+1KDfMbDl6zn18m4urW6ZWlsAkt1WJtBsHTpt9G1lZ+pcbbXsnEqe7QBmEtmRLd6Xoodpgb9xKpNv1K6lfKD8lKnVJohozZz1gOH9mg8Q87IzU52UvJB0xbSIOBcwphRP26asOpvGTFs4bdlWuqbnm9PSuvzW3jVlci+oMpNaYkbfxcQCJHM8jbnxVHaq0akRGk9AnODoOyCoTXRQdyeSpQF6HzCaDfQ7vlTntOfRNs2Zn6EvfRcAc0RXH2BKYIzKU5zqKomYWmB6qN6XujWlIlVaft2UsqlVtpqnAUT06b3GxOxgBEl6p53sm+llb1X3BEfv0jYxZd7n+BB3lok+E7yTUY2/0O08C2nLqKaM8SVazDZn29VZG9sZReAo/X2dBuZwTJVZwdR8RNxGnWJS36VqI3aZupWfRJ3gytFp4aMOTkdpMimGZ9+pRWWzswGghNe8/em154w17vqQPzEd4LReGiXPvN/RMMer9uf7sqK8y8Gr7SnZ06FLemN4pqjAkXIxHRACUBpjao4EeJpY368awAAQ5yBGReTpfPUsYvndzGAGYbJh3WNE37BFMaIpT9PLmFVcoOBM0zS/I2r1z2CP76pr3vt21uNvjtMczxY8xQvmFDJGBoicOPXN6WRjrB/TvudMo0UadBtLmBK/9fi3zM70JmBzYSgNkhldNUDMaGDN1yNAnZ9z60lc38WImOYeofNAS1SMyN2LqHmPGEneJG711acZpSteUNkpwQHOu/YkNB5jyC4jRCHTNmsUWi2AknRNjdF6frGMGUiaAJp/R+9mSHNs2zJbEDa2uY4w7fl8Xj/zneaMZM6IjmmABtO8dUpv6mN6xjPgkep1V665aY3nzECeOlqf1qni051lF9onU4puTVs6GZQT1vRrTgvrS8wuEbS62qqWCvW+E8s61Krf7HVxegRO+qKFd42n+tWXaQsoMT2QT40QQPo2wQGYLbinZk3rFIXt21l3AuHeVHy3PTvOIBpoqqhn0o+lIndiSMfKtNDRuUIGn1OXZNWOe2fjxuSYMAmMqW2Z6sCmzrvNe1auPupT5wv0vWed+jV3I0/Hq/OJZ/mYWD87MXRqicbTPL5dzrUTndpvUV/aEVT5vHf0ysylzYDW+/oxHeKEKucbyNLMaY6EemuCJ7h2c4WoY2O3jOms2Y5xbdB1YgIg4sfMmBuztuDSVoyeBxrPVS1/N43sFM+YrUxqu7/rX2cA9L79cO3T69xCzGrBpFU0DKlftZUmOKjt2pwarc2fnbc062k1MABjbnW0B3B7rmIaqY2vc6NtB34WU9iasJy/HOum0asGmAObjMvrj0kd4qSxiKgzAaABtOM3L9Y9xvm2wU9wRPiicSE/ydne6/Mk+CSm8hG8fkdQ9wnUmF+bE2Rpi9legEoASCqC9k8vOvTCfVsuYNfm1GaTFx2hN0HqfVrIWP2urbTztr/bvs6FKW2s5wNku1KDdbp3HRShka55gFTBoDRIar7f3ed6+NYGZz4mgbaStl3pyseo/iQk9RojA0DqvvH1HemYjJomIN9kgrmUqinRnTG0BVsSGaMDWSYurTfr6tk0ufFG+dpKI/fd3Gza99NMAg2hzgytAJhE3qrQnJumFKmyKUkd+5o0T/AkITOyFVgCV0yNiantvsvxnA5lPknSMG1z45lMTL1qc5qBLZizx9O5a1o1g17ar50kLtpUZ+cd59Q2xZ4+QFroIBMz+aLPfeseyDrXcGqJzJb7DFgB7exrZmY3o2Iz0pSERoC85Jl54u+ibwVn8joxbnagcpPR0+ZlFrQ7Q8J58ZgyzcTsX31ojtzqWoxP8mPOJOY0W1NDKas97ecTNQPou9kf3xTommHqNEdlt5J+kAmOD75NU3TUa8zvhPNMcQ65MlO4EpZtO5nUdXewzoeIDoeaBRroVkKmap+2uMGmfurEdFBmXDoib6WpU7YNTtls9/TyU621UT8iSMyvrlnWt41NfwAns5FvM83KlPopafWhcDWaEoS8+MA7zVvtRqsAlKbxfPoB2fpmF9NvmzOS6otXTWvnTCpTsR4VG4FSLVspCVGIVYw5NZYNmid9xaTU0lbCAtr0J3Iq2//ecafTmcqjzldoVuAbGx078bRNlNXR4VPzdx5zRMmMRYOYH2jmLtwIP81E8f+mghE4hxozpqlL2yUEc2HJuMzTc7wzrdO+b6V5gicnOjPVGBtTQpiftvoAqbSkJy3QwKezMYMj04Z2vm715ey0LtB8vLq2jInZqbapiXJ6JiBO9PcEUNojyW5p1nPfBZ4OUAxIqdxAufXGA0IEbjxzHP6eDlqOGeYUPNMfDPc7D30GvPr7oPjMdKSnRqHJAl18iZ6BXF8AcPUBWmTYepdTsmfQZ6uGGljgmNKddE3Jn/PbANGsAlBSX+7eT6meZ+BOOzgBkXM0tdlk6Jw6pUGmFonpTeOmeePModl0wAJSZ/fO+ict0iip7EwKqZ2zh1R/ZQPlFMjp5KoPANJA9WP6DfFm8sj3uym5sxMhd6s6AkwrfwYxibFV24ElDbAFUsis7WkOejaldUpk3xpoMwGEoEb1U5uTOUl5zK58GmNrJgJqfY9xMSiGTE0yD9+OhjPO0N8Y2LimVszUTbpstd3WF9IPGgQvAnRl+i8jc+yTnv8HiieS9fcA8gsAAAAASUVORK5CYII=";
                String software = version.get("name").getAsString();
                int protocol = version.get("protocol").getAsInt();
                int online = players.get("online").getAsInt();
                int max = players.get("max").getAsInt();

                if (favicon != null && favicon.isJsonNull())
                    icon = favicon.getAsString();


            } catch (IOException io) {
                MessageUtils.message(sender, "&6&l» &7Failed to connect to &6" + hostname);
            } catch (Exception exception) {
                MessageUtils.error(sender, exception);
                exception.printStackTrace();
            }
        } else
            MessageUtils.usage(sender, label, "<address[:port]>");
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.then(Arguments.stringTypeWord("address")).build();
    }

}
